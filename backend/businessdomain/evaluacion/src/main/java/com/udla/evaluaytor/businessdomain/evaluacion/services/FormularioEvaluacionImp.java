package com.udla.evaluaytor.businessdomain.evaluacion.services;

import com.udla.evaluaytor.businessdomain.evaluacion.models.FormularioEvaluacion;
import com.udla.evaluaytor.businessdomain.evaluacion.models.Proveedor;
import com.udla.evaluaytor.businessdomain.evaluacion.models.Perito;
import com.udla.evaluaytor.businessdomain.evaluacion.repositories.FormularioEvaluacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class FormularioEvaluacionImp implements FormularioEvaluacionService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private FormularioEvaluacionRepository formularioEvaluacionRepository;

    public FormularioEvaluacion getFormularioEvaluacion(Long formularioId, Long proveedorId, Long peritoId) {
        // Obtén el FormularioEvaluacion desde el repositorio
        FormularioEvaluacion formularioEvaluacion = formularioEvaluacionRepository.findById(formularioId)
            .orElseThrow(() -> new RuntimeException("Formulario no encontrado"));

        // Llama al microservicio de proveedor para obtener la información del proveedor
        WebClient webClient1 = webClientBuilder.build();
        Mono<Proveedor> proveedorMono = webClient1.get()
            .uri("http://localhost:8086/api/empresa/proveedor/findbyid/{id}", proveedorId)
            .retrieve()
            .bodyToMono(Proveedor.class);

        Proveedor proveedor = proveedorMono.block();
        formularioEvaluacion.setProveedor(proveedor);

        WebClient webClient2 = webClientBuilder.build();
        Mono<Perito> peritoMono = webClient2.get()
            .uri("http://localhost:8086/api/empresa/perito/findbyid/{id}", peritoId)
            .retrieve()
            .bodyToMono(Perito.class);

        Perito perito = peritoMono.block();
        formularioEvaluacion.setPerito(perito);
        

        return formularioEvaluacion;
    }
}
