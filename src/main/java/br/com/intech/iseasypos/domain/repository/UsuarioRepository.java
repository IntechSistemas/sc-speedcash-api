package br.com.intech.iseasypos.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.intech.iseasypos.domain.model.Empresa;
import br.com.intech.iseasypos.domain.model.Usuario;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Integer>{
	public List<Usuario> findByEmpresa(Empresa empresa);
	public Usuario findByLoginAndSenha(String login, String senha);
	public Optional<Usuario> findByIdAndEmpresa(Integer usuarioId, Empresa empresa);
	@Transactional
	@Modifying
	@Query("delete from Usuario where id = :usuarioId and empresa_id = :empresaId")
	public void deleteByIdAndEmpresa(Integer usuarioId, Integer empresaId);
	public Optional<Usuario> findByEmailAndEmpresa(String email, Empresa empresa);
	
}
