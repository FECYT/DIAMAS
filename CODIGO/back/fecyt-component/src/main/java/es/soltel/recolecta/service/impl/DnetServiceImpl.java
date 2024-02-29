package es.soltel.recolecta.service.impl;

import java.net.ConnectException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXParseException;

import es.soltel.recolecta.anottation.NoLogging;
import es.soltel.recolecta.response.RepositoryResponse;
import es.soltel.recolecta.response.UserResponse;
import es.soltel.recolecta.service.DnetService;
import es.soltel.recolecta.service.RepositoryService;
import es.soltel.recolecta.service.InstitucionService;
import es.soltel.recolecta.service.UserService;
import es.soltel.recolecta.service.XMLResponseService;
import es.soltel.recolecta.vo.InstitucionVO;
import es.soltel.recolecta.vo.RepositoryVO;
import es.soltel.recolecta.vo.UserVO;

@Component
public class DnetServiceImpl implements DnetService {

    @Autowired
    private RequestHttpServiceImpl requestService;

    @Autowired
    private XMLResponseService xmlResponseService;

    @Autowired
    private RepositoryService repositoryService;
    
    @Autowired
    private InstitucionService institucionService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private UserService userService;



    @NoLogging
    public UserResponse getUserDNETByEmail(String emailUserDNET) throws Exception {
        return null;
    }

    @NoLogging
    public List<UserResponse> getUsersDNET() throws Exception {
        return null;
    }

    public List<RepositoryResponse> getRepositoriesDNETByEmail(String emailUserDNET) throws Exception {
        return null;
    }

    @Override
    public List<RepositoryResponse> getRepositoriesDNETByIdRepository(String idRepositoryDNET) throws Exception {
        return null;
    }

    @Override
    public RepositoryVO createDnetRepositoryIfNotExist(RepositoryResponse repositorioDnet) {
        RepositoryVO repositorioBuscado = repositoryService.findByRepositoryIdDnet(repositorioDnet.getDnetId());
        InstitucionVO institucionBuscada = institucionService.findByInstitucionName(repositorioDnet.getOfficialName());
        if (repositorioBuscado.getId() == null) {
            repositorioBuscado.setnDeleteState(1);
            // Buscar institucion por nombre, si no existe crearla y enlazarlo
            if(institucionBuscada == null) {
                institucionService.create(institucionBuscada);
                repositorioBuscado.setInstitucion(institucionBuscada);
            } else {
                repositorioBuscado.setInstitucion(institucionBuscada);
            }
            return repositoryService.create(repositorioBuscado);

        } else {
            return repositorioBuscado;
        }
    }

    @Override
    public List<UserVO> getEvaluators() throws Exception {

        List<UserResponse> totalUsers = getUsersDNET();
        List<UserResponse> evaluatorsResponse = totalUsers.stream()
                .filter(user -> user.getRoles().contains("evaluator"))
                .collect(Collectors.toList());

        List<UserVO> evaluators = new ArrayList<>();

        for (UserResponse evaluator : evaluatorsResponse) {

            UserVO newEvaluator = new UserVO();

            //newEvaluator.setDnetId(evaluator.getUserId());
            newEvaluator.setLastLogin(null);
            newEvaluator.setNDeleteState(1);
            newEvaluator.setNombre(LoginServiceImpl.emailAFormatoNombre(evaluator.getEmail()));
            newEvaluator.setEmail(evaluator.getEmail());

            evaluators.add(newEvaluator);
        }

        return evaluators;
    }

}
