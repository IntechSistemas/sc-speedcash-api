package br.com.intech.iseasypos.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.intech.iseasypos.domain.exception.EntidadeEmUsoException;
import br.com.intech.iseasypos.domain.exception.NegocioException;
import br.com.intech.iseasypos.domain.exception.UsuarioNaoEncontradoException;
import br.com.intech.iseasypos.domain.model.Empresa;
import br.com.intech.iseasypos.domain.model.Usuario;
import br.com.intech.iseasypos.domain.repository.UsuarioRepository;

@Service
public class UsuarioService {
	private static final String MSG_USUARIO_EM_USO = "Usuario de codigo %d não pode ser removido pois está em uso!";
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private EmpresaService empresaService;
	@Autowired
	private CargoService cargoService;

	public List<Usuario> findAll(Integer empresaId) {
		Empresa empresa = empresaService.findById(empresaId);
		return usuarioRepository.findByEmpresa(empresa);
	}

	public Usuario findById(Integer usuarioId, Integer empresaId) {
		Empresa empresa = empresaService.findById(empresaId);
		return usuarioRepository.findByIdAndEmpresa(usuarioId, empresa)
				.orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
	}

	@Transactional
	public Usuario save(Usuario usuario, Integer empresaId) {
		usuarioRepository.detach(usuario);
		
		Empresa empresa = empresaService.findById(empresaId);
		usuario.setEmpresa(empresa);

		usuario.getCargos().forEach(cargo -> {
			cargoService.findById(cargo.getId(), empresa.getId());
		});

		
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmailAndEmpresa(usuario.getEmail(), empresa);
		
		if(usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new NegocioException(
				String.format(
					"Já existe usuário cadastrado com o e-mail: %s ", usuario.getEmail()
				)
			);
		}
		return usuarioRepository.save(usuario);
	}

	@Transactional
	public void delete(Integer usuarioId, Integer empresaId) {
		try {
			usuarioRepository.deleteByIdAndEmpresa(this.findById(usuarioId, empresaId).getId(), empresaId);
			usuarioRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new UsuarioNaoEncontradoException(usuarioId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_USUARIO_EM_USO, usuarioId));
		}
	}
	
	@Transactional
    public void alterarSenha(Integer empresaId, Integer usuarioId, String senhaAtual, String novaSenha) {
        Usuario usuario = this.findById(usuarioId, empresaId);
        
        if (usuario.senhaNaoCoincideCom(senhaAtual)) {
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
        }
        
        usuario.setSenha(novaSenha);
    }

	public Usuario login(String login, String senha) {

			Usuario usuario = usuarioRepository.findByLoginAndSenha(login, senha);
			
			if(usuario != null)
				return usuario;
			
			throw new UsuarioNaoEncontradoException();
	}
}
