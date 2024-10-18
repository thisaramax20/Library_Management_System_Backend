package edu.icet.crm.service;

import edu.icet.crm.service.custom.impl.AdminServiceImpl;
import edu.icet.crm.util.ServiceType;

public class ServiceFactory {
    private static ServiceFactory instane;
    private ServiceFactory(){}
    public static ServiceFactory getInstance(){
        return instane==null?instane=new ServiceFactory():instane;
    }

}
