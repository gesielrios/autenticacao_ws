package br.com.grios.ws;

import java.sql.SQLException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import br.com.grios.dao.ServiceDao;



@WebService
public class AutenticacaoWebService {
	
	@WebMethod(operationName="autenticado")
    public Boolean autenticado(@WebParam(name = "login") String login,
            @WebParam(name = "senha") String senha) {

        Boolean result = Boolean.FALSE;

        ServiceDao serviceDao = new ServiceDao();
        try {
            result = serviceDao.autenticado(login, senha);
            
        } catch (SQLException ex) {
        	return result;
        }

        return result;

    }

    @WebMethod(operationName="authorizate")
    public Boolean autorizado(@WebParam(name = "login") String login,
            @WebParam(name = "role") String role) {

        Boolean result = Boolean.FALSE;

        ServiceDao serviceDao = new ServiceDao();
        try {
            result = serviceDao.autorizado(login, role);
            
        } catch (SQLException ex) {
        	return result;
        }

        return result;

    }

}
