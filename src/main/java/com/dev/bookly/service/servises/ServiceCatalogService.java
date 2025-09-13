package com.dev.bookly.service.servises;

import java.util.List;

public interface ServiceCatalogService {
    public List<ServiceResponseDTO> listServices(Long businessId);
    
}
