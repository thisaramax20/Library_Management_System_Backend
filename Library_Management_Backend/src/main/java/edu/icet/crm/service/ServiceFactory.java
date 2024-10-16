package edu.icet.crm.service;

import edu.icet.crm.service.custom.impl.AdminServiceImpl;
import edu.icet.crm.util.ServiceType;

public class ServiceFactory {
    private static ServiceFactory instane;
    private ServiceFactory(){}
    public ServiceFactory getInstance(){
        return instane==null?instane=new ServiceFactory():instane;
    }

    public <T>T getService(ServiceType serviceType){
        switch (serviceType){
            case ADMIN:return (T) new AdminServiceImpl();
        }
        return null;
    }
}
