package com.mdl.student.log.aspect;

import com.mdl.student.entity.StudentEntity;
import com.mdl.student.request.CreateStudentRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    // pointcut for all methods in your controller package
    @Pointcut("within(com.mdl.student.controller..*)")
    public void controllerLayer() {}

    @Before("controllerLayer()")
    public void logBefore(JoinPoint jp) {
        log.info("→ [CONTROLLER] entering {}.{}() with args={}",
                jp.getSignature().getDeclaringType().getSimpleName(),
                jp.getSignature().getName(),
                jp.getArgs());
    }

    @AfterReturning(pointcut = "controllerLayer()", returning = "retVal")
    public void logAfter(JoinPoint jp, Object retVal) {
        log.info("← [CONTROLLER] exiting {}.{}() → returned={}",
                jp.getSignature().getDeclaringType().getSimpleName(),
                jp.getSignature().getName(),
                retVal);
    }


    // SERVICE LAYER — CRUD
    @Pointcut("execution(* com.mdl.student.service.impl.StudentServiceImpl.create(..)) && args(request)")
    public void createOp(CreateStudentRequest request) {}

    @AfterReturning(pointcut="createOp(request)", returning="entity")
    public void logCreateSuccess(CreateStudentRequest request, StudentEntity entity) {
        log.info("✔ [SERVICE] createStudent() succeeded: id={} name={}",
                entity.getId(),
                entity.getName());
    }



    @Pointcut("execution(* com.mdl.student.service.impl.StudentServiceImpl.update*(..))")
    public void updateOp() {}

    @AfterReturning(pointcut="updateOp()", returning="entity")
    public void logUpdateSuccess(JoinPoint jp, StudentEntity entity) {
        log.info("✔ [SERVICE] {} succeeded: id={} name={}",
                jp.getSignature().getName(),
                entity.getId(),
                entity.getName());
    }

    @AfterThrowing(pointcut="updateOp()", throwing="ex")
    public void logUpdateFailure(JoinPoint jp, Throwable ex) {
        log.error("✘ [SERVICE] {} failed – {}",
                jp.getSignature().getName(),
                ex.getMessage(), ex);
    }


    @Pointcut("execution(* com.mdl.student.service.impl.StudentServiceImpl.deleteStudentById(..)) && args(id)")
    public void deleteOp(String id) {}

    @AfterReturning(pointcut="deleteOp(id)", returning="entity")
    public void logDeleteSuccess(String id, StudentEntity entity) {
        log.info("✔ [SERVICE] deleteStudentById({}) succeeded (marked deletedAt={})",
                id,
                entity.getDeletedAt());
    }

    @AfterThrowing(pointcut="deleteOp(id)", throwing="ex")
    public void logDeleteFailure(String id, Throwable ex) {
        log.error("✘ [SERVICE] deleteStudentById({}) failed – {}",
                id, ex.getMessage(), ex);
    }
}
