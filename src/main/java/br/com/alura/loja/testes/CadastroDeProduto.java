package br.com.alura.loja.testes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.utils.JPAUtil;

public class CadastroDeProduto {
	
	public static void main(String[] args) {
		cadatrarProduto();


		EntityManager em = JPAUtil.getEntityMenager();


		ProdutoDao  produtoDao = new ProdutoDao(em);

		Produto p = produtoDao.buscarPorId(1L);

		System.out.println(p.getPreco());

		List<Produto> todos = produtoDao.buscarTodos();
		todos.forEach(p2 -> System.out.println(p.getNome()));

		List<Produto> todosnome = produtoDao.buscarPorNome("Xiaomi Redmi");
		todosnome.forEach(p2 -> System.out.println(p.getNome()));

		List<Produto> categoria = produtoDao.buscarPorDaCategoria("CELULARES");
		categoria.forEach(p2 -> System.out.println(p.getNome()));

		BigDecimal preco = produtoDao.buscarPorPrecoComnome("Xiaomi Redmi");
		System.out.println("Preço do produto: " + preco);
	}

	public static void cadatrarProduto() {

		Categoria celulares = new Categoria("CELULARES");
		Produto celular = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal("800"),LocalDate.now(), celulares );


		EntityManager em = JPAUtil.getEntityMenager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		CategoriaDao categoriaDao = new CategoriaDao(em);

		em.getTransaction().begin();

		categoriaDao.cadastrar(celulares);
		produtoDao.cadastrar(celular);

		em.getTransaction().commit();
		em.close();



//		Categoria celulares = new Categoria("Celulares");
//		Produto celular = new Produto("Xiaomi Redmi","Muito legal", new BigDecimal("800"), LocalDate.now(), celulares);
//
//		EntityManager em = JPAUtil.getEntityManager();
//		EntityManager em = JPAUtil.getEntityMenager();
//
//		em.getTransaction().begin();
//		ProdutoDao   produtoDao   = new ProdutoDao(em);
//		CategoriaDao categoriaDao = new CategoriaDao(em);
//
//		em.getTransaction().commit();
//
//		em.getTransaction().begin();
//
//		em.persist(celulares); //persiste
//		celulares.setNome("Novo"); // atualiza o nome
//
//
//		em.flush(); //faz o flush e dispara o update

//		em.clear();
//
//		celulares = em.merge(celulares); // volta pro estado menaged, fazendo o merge
//		celulares.setNome("123"); // atualiza o nome
//		em.flush();
		//ainda está menaged pois n dei clear nem close

		//em.remove(celulares);
		//em.flush();
	}

}
