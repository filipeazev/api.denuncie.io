/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package io.denuncie.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Filipe
 */
public class JPAUtil {
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory(Constantes.TESTESPU);
    private  EntityManager em;
    
    public  EntityManager getEntityManager(){
        if(!em.isOpen()){
           em = emf.createEntityManager();
        }
        return em;
    }
    
    public void close(){
        em.close();
    }
    
}
