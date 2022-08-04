package br.com.alura.loja.testes;

import br.com.alura.loja.dao.ClienteDao;
import br.com.alura.loja.dao.PedidoDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.Cliente;
import br.com.alura.loja.modelo.ItemPedido;
import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.testes.CadastroDeProduto;
import br.com.alura.loja.utils.JPAUtil;
import br.com.alura.loja.vo.RelatorioDeVendaVo;

import javax.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.List;

import static br.com.alura.loja.testes.CadastroDeProduto.cadatrarProduto;

public class CadastroDePedido {

    public static void main(String[] args) {

        cadatrarProduto(); // cadastra o produto
        cadastrarpedido(); //cadastra pedido
        geraRelatorio();  // cadastra relatorio


        EntityManager em = JPAUtil.getEntityMenager(); //Cria o entity maneger

        Pedido pedido = em.find(Pedido.class,1L);
        PedidoDao pedidoDao = new PedidoDao(em);

        Pedido pedido1 = pedidoDao.buscarPedidoComCliente(1L);
        em.close();
        System.out.println("data do pedido: " +pedido1);
    }

    private static void cadastrarpedido() {
        EntityManager em = JPAUtil.getEntityMenager();                          //Cria o entity maneger
        em.getTransaction().begin();
        ProdutoDao produtoDao = new ProdutoDao(em);                             //Cria o produtoDao
        PedidoDao  pedidoDao  = new PedidoDao(em);                              //Cria o pedidoDao
        ClienteDao clienteDao = new ClienteDao(em);                             //Cria o clienteDao
        Cliente    cliente    = new Cliente("Gian", "09017144963");   //Cria o cliente
        Pedido     pedido     = new Pedido(cliente);                            //Cria o pedido
        Produto produto = produtoDao.buscarPorId(1L);                             //Carrega o produto do DB pra variavel
        pedido.adicionarItem(new ItemPedido(10, pedido, produto));    //Adiciona o item
        clienteDao.cadastrar(cliente);
        pedidoDao.cadastrar(pedido);                                            //Cadastra o pedido
        em.getTransaction().commit();
    }

    private static void geraRelatorio(){
        EntityManager em = JPAUtil.getEntityMenager(); //Cria o entity maneger
        PedidoDao  pedidoDao  = new PedidoDao(em);     //Cria o pedidoDao
        BigDecimal totalVendido = pedidoDao.valorTotalVendido();
        System.out.println(totalVendido);
        List<RelatorioDeVendaVo> relatorio = pedidoDao.relatorioDeVendas();
        relatorio.forEach(System.out::println);
    };
}
