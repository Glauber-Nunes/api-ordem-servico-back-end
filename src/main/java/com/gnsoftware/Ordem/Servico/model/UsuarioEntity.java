package com.gnsoftware.Ordem.Servico.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario")
public class UsuarioEntity  {

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Email
    @Column(unique = true)
    private String email;
    private String senha;
    @Setter(AccessLevel.NONE) // cancela a criacçao do setter
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_usuario_permissao", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "permissao_id"))
    private List<PermissaoEntity> permissoes = new ArrayList<>();

}
