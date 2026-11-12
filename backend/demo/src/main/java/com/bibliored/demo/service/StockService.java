package com.bibliored.demo.service;

import com.bibliored.demo.enums.MovimientoInventario;
import com.bibliored.demo.model.Libro;
import com.bibliored.demo.model.Stock;
import com.bibliored.demo.model.Sucursal;

import java.util.List;

public interface StockService {
    void movilizarStock (Libro libro, Sucursal sucursal, int unidades, MovimientoInventario movimiento);
    List<Stock> consultarStock();
}
