package com.galaxiaburguer.repository;
import com.galaxiaburguer.enums.StatusPedido;
import com.galaxiaburguer.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByStatus(StatusPedido status);
}