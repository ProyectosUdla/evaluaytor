package com.udla.evaluaytor.businessdomain.evaluacion.services;

import com.udla.evaluaytor.businessdomain.evaluacion.models.FormularioEvaluacion;

public interface FormularioEvaluacionService {

    public FormularioEvaluacion getFormularioEvaluacionWithProveedor(Long formularioId, Long proveedorId);

}
