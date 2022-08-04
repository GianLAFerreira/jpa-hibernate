package br.com.alura.loja.dao;

import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.modelo.Produto;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class ProdutoDao {
    private EntityManager em;

    public ProdutoDao(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Produto produto){
        this.em.persist(produto);
    }

    public Produto buscarPorId(Long id){
        return em.find(Produto.class, id);
    }

    public List<Produto> buscarTodos(){
        return em.createQuery("select p from Produto p", Produto.class).getResultList();
    }

    public List<Produto> buscarPorNome(String nome){
        return em.createQuery("select p from Produto p where p.nome = :nome", Produto.class).setParameter("nome", nome).getResultList();
    }

    public List<Produto> buscarPorDaCategoria(String nome){
        return em.createQuery("select p from Produto p where p.categoria.nome = :nome", Produto.class).setParameter("nome", nome).getResultList();
    }

    public BigDecimal buscarPorPrecoComnome(String nome){
        return em.createQuery("select p.preco from Produto p where p.nome = :nome", BigDecimal.class).setParameter("nome", nome).getSingleResult();
    }

}
