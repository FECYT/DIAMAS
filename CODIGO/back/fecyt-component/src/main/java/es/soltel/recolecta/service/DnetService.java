package es.soltel.recolecta.service;

import java.util.List;

import es.soltel.recolecta.response.RepositoryResponse;
import es.soltel.recolecta.response.UserResponse;
import es.soltel.recolecta.vo.RepositoryVO;
import es.soltel.recolecta.vo.UserVO;

public interface DnetService {
    public es.soltel.recolecta.response.UserResponse getUserDNETByEmail(String emailUserDNET) throws Exception;

    public List<UserResponse> getUsersDNET() throws Exception;

    public List<RepositoryResponse> getRepositoriesDNETByEmail(String emailUserDNET) throws Exception;

    List<RepositoryResponse> getRepositoriesDNETByIdRepository(String idRepositoryDNET) throws Exception;

    RepositoryVO createDnetRepositoryIfNotExist(RepositoryResponse repositorioDnet);

    List<UserVO> getEvaluators() throws Exception;

}
