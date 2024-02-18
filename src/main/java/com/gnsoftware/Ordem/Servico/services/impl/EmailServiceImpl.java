package com.gnsoftware.Ordem.Servico.services.impl;


import com.gnsoftware.Ordem.Servico.model.ClienteEntity;
import com.gnsoftware.Ordem.Servico.model.OrdemServicoEntity;
import com.gnsoftware.Ordem.Servico.services.EmailService;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    String remetenteproperties;
    @Autowired
    private Configuration configuration;



    @Override
    public void enviarEmailOSAberta(ClienteEntity clienteEntity, OrdemServicoEntity ordemServico) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        Map<String, Object> propriedades = new HashMap<>();
        propriedades.put("nome", clienteEntity.getNome()); // remetente
        propriedades.put("mensagem1", " SEU SERVIÇO FOI ABERTO COM SUCESSO, EM BREVE VOÇE RECEBERA UM EMAIL QUANDO SEU SERVIÇO FOR CONCLUIDO :)");
        propriedades.put("mensagem2", "Importante: Anote o número de protocolo para referência futura. Este número é essencial para acompanhar o status do seu serviço. Seu Protocolo é: " + ordemServico.getProtocolo());
        propriedades.put("mensagem3", " Valor Total: " + ordemServico.getValorTotalOrdem());

        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setSubject("Informaçoes do seu serviço");
            mimeMessageHelper.setFrom(remetenteproperties);
            mimeMessageHelper.setTo(clienteEntity.getEmail());

            mimeMessageHelper.setText(this.getConteudoTemplate(propriedades), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void enviarEmailServicoFinalizado(OrdemServicoEntity ordemServicoEntity) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        Map<String, Object> propriedades = new HashMap<>();
        propriedades.put("nome", ordemServicoEntity.getClienteEntity().getNome());
        propriedades.put("mensagem1", "Gostaríamos de informar que o serviço solicitado foi concluído com sucesso. O número de protocolo associado a este encerramento é " + ordemServicoEntity.getProtocolo());
        propriedades.put("mensagem2", "Data de Término: " + ordemServicoEntity.getDataFechamento() + "Agradecemos pela confiança depositada em nossos serviços. Em caso de dúvidas adicionais ou necessidade de suporte futuro, não hesite em entrar em contato.");
        propriedades.put("mensagem3", " VOLTE SEMPRE");

        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setSubject("Serviço Finalizado Com Sucesso"); // titulo do email
            mimeMessageHelper.setFrom(remetenteproperties);
            mimeMessageHelper.setTo(ordemServicoEntity.getClienteEntity().getEmail());

            mimeMessageHelper.setText(this.getConteudoTemplate(propriedades), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    private String getConteudoTemplate(Map<String, Object> model) {
        StringBuffer content = new StringBuffer();

        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate("email-flth"), model));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }


}
