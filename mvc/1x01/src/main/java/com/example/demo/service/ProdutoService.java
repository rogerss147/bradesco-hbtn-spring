package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.model.Produto;

@Service
public class ProdutoService {

    private final List<Produto> produtos = new ArrayList<>();
    private Long contadorId = 1L;

    public List<Produto> listarProdutos() {
        return new ArrayList<>(produtos);
    }

    public Produto adicionarProduto(Produto produto) {
        produto.setId(contadorId++);
        produtos.add(produto);
        return produto;
    }

    public Produto atualizarProduto(Long id, Produto produtoAtualizado) {
        Optional<Produto> produtoExistente = produtos.stream()
                .filter(produto -> produto.getId().equals(id))
                .findFirst();

        if (produtoExistente.isPresent()) {
            Produto produto = produtoExistente.get();
            produto.setNome(produtoAtualizado.getNome());
            produto.setPreco(produtoAtualizado.getPreco());
            return produto;
        }

        return null;
    }

    public boolean deletarProduto(Long id) {
        return produtos.removeIf(produto -> produto.getId().equals(id));
    }
}
