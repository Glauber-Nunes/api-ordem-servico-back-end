package com.gnsoftware.Ordem.Servico.security;


import com.gnsoftware.Ordem.Servico.model.enums.Perfil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserSpringSecurity implements UserDetails {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private List<Perfil> perfils = new ArrayList<>();

    public UserSpringSecurity(Long id, String nome, String email, String senha, List<Perfil> perfils) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.perfils = perfils;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return perfils.stream().map(perfil ->
                new SimpleGrantedAuthority(perfil.getDescricao())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
