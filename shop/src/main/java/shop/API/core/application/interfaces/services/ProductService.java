package shop.API.core.application.interfaces.services;

import org.springframework.stereotype.Service;
import shop.API.core.application.dtos.products.GetProductDto;

@Service
public interface ProductService {
    GetProductDto getById(Long id);
}
