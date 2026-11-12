package com.bibliored.demo.service;

import com.bibliored.demo.dto.InventarioRequestDTO;
import com.bibliored.demo.enums.MovimientoInventario;
import com.bibliored.demo.exceptions.BusinessExceptions;
import com.bibliored.demo.model.Stock;
import com.bibliored.demo.model.Libro;
import com.bibliored.demo.model.Sucursal;
import com.bibliored.demo.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public void movilizarStock(Libro libro, Sucursal sucursal, int unidades, MovimientoInventario movimiento) {
        boolean entrada = movimiento == MovimientoInventario.ENTRADA;
        int variacion = entrada ? unidades : -unidades;

        Stock stock = stockRepository.findBySucursalAndLibro(sucursal, libro)
                .orElseGet(() -> new Stock(sucursal, libro, 0));

        int nuevo = stock.getDisponible() + variacion;
        if (nuevo < 0){
            throw new BusinessExceptions("Stock insuficiente para el libro: " + libro.getTituloLibro());
        }

        stock.setDisponible(nuevo);
        stockRepository.save(stock);
    }

    @Override
    public List<Stock> consultarStock() {
        return stockRepository.findAll();
    }
}
