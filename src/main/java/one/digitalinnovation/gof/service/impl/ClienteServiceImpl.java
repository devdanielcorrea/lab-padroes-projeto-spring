package one.digitalinnovation.gof.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import one.digitalinnovation.gof.exception.ClienteInvalidoException;
import one.digitalinnovation.gof.model.Cliente;
import one.digitalinnovation.gof.model.ClienteRepository;
import one.digitalinnovation.gof.model.Endereco;
import one.digitalinnovation.gof.model.EnderecoRepository;
import one.digitalinnovation.gof.service.ClienteService;
import one.digitalinnovation.gof.service.ViaCepService;

/**
 * Implementação da Strategy ClienteService, a qual pode ser injetada pelo
 * Spring. Como essa classe é um Service, ela será tratada como um Singleton.
 *
 * Implementação original: falvojr Validações e adaptações: devdanielcorrea
 */
@Service
public class ClienteServiceImpl implements ClienteService {

	// Singleton: injetar os componentes do Spring.
	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private ViaCepService viaCepService;

	// Strategy: implementar os métodos definidos na interface.
	// Facade: abstrair integrações com subsistemas.

	@Override
	public Iterable<Cliente> buscarTodos() {
		return clienteRepository.findAll();
	}

	@Override
	public Cliente buscarPorId(Long id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.get();
	}

	@Override
	public void inserir(Cliente cliente) {
		validarCliente(cliente);
		salvarClienteComCep(cliente);
	}

	@Override
	public void atualizar(Long id, Cliente cliente) {
		validarCliente(cliente);

		Optional<Cliente> clienteBd = clienteRepository.findById(id);

		if (clienteBd.isPresent()) {
			cliente.setId(id);
			salvarClienteComCep(cliente);
		}
	}

	@Override
	public void deletar(Long id) {
		clienteRepository.deleteById(id);
	}

	/**
	 * Valida os dados obrigatórios antes de cadastrar ou atualizar um cliente.
	 *
	 * @param cliente cliente recebido pela API
	 * @throws ClienteInvalidoException quando os dados forem inválidos
	 */
	private void validarCliente(Cliente cliente) {
		if (cliente == null) {
			throw new ClienteInvalidoException("Os dados do cliente são obrigatórios.");
		}

		if (cliente.getNome() == null || cliente.getNome().isBlank()) {
			throw new ClienteInvalidoException("O nome do cliente é obrigatório.");
		}

		if (cliente.getEndereco() == null) {
			throw new ClienteInvalidoException("O endereço do cliente é obrigatório.");
		}

		String cep = cliente.getEndereco().getCep();

		if (cep == null || !cep.matches("\\d{8}")) {
			throw new ClienteInvalidoException("O CEP deve conter exatamente 8 números.");
		}
	}

	private void salvarClienteComCep(Cliente cliente) {
		String cep = cliente.getEndereco().getCep();

		Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
			Endereco novoEndereco = viaCepService.consultarCep(cep);

			enderecoRepository.save(novoEndereco);
			return novoEndereco;
		});

		cliente.setEndereco(endereco);
		clienteRepository.save(cliente);
	}
}