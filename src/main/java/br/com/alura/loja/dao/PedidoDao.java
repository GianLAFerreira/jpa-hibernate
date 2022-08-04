package br.com.alura.loja.dao;

import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.vo.RelatorioDeVendaVo;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class PedidoDao {
    private EntityManager em;

    public PedidoDao(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Pedido pedido){
        this.em.persist(pedido);
    }

    public BigDecimal valorTotalVendido(){
        return em.createQuery("select sum(p.valorTotal) from Pedido p", BigDecimal.class).getSingleResult();
    }

    public List<RelatorioDeVendaVo> relatorioDeVendas(){
        return em.createQuery("SELECT new br.com.alura.loja.vo.RelatorioDeVendaVo(c.nome, sum(b.quantidade), max(a.data))"
                           + "from Pedido    a   "
                           + "join a.itens   b   "
                           + "join b.produto c   "
                           + "group by c.nome    "
                           + "order by b.quantidade desc", RelatorioDeVendaVo.class).getResultList();
    }

    public Pedido buscarPedidoComCliente(Long id){
        return em.createQuery("SELECT p from Pedido p join fetch p.cliente where p.id = :id", Pedido.class).setParameter("id", id).getSingleResult();
    }
}
