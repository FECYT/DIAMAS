package es.soltel.recolecta.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.soltel.recolecta.entity.RepositoryEntity;
import es.soltel.recolecta.repository.RepositoryRepository;
import es.soltel.recolecta.service.DnetService;
import es.soltel.recolecta.converters.RepositoryConverter;
import es.soltel.recolecta.vo.RepositoryVO;

@Service
public class RepositoryService implements es.soltel.recolecta.service.RepositoryService {

    @Autowired
    private RepositoryRepository repository;

    @Autowired
    private DnetService dnetService;

    @Override
    public List<RepositoryVO> getAll() {
        return repository.findAllExcludeDeleted().stream().map(RepositoryConverter::convertEntityToVO)
                .collect(Collectors.toList());
    }

    @Override
    public RepositoryVO getById(Long id) {
        return RepositoryConverter.convertEntityToVO(repository.findById(id).orElse(null));
    }

    @Override
    @Transactional
    public RepositoryVO create(RepositoryVO vo) {
        return RepositoryConverter.convertEntityToVO(repository.save(RepositoryConverter.convertVOToEntity(vo)));
    }

    @Override
    @Transactional
    public RepositoryVO update(RepositoryVO vo) {
        return RepositoryConverter.convertEntityToVO(repository.save(RepositoryConverter.convertVOToEntity(vo)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        RepositoryEntity entity = repository.findById(id).orElse(null);
        if (entity != null) {
            entity.setnDeleteState(2);
            repository.save(entity);
        }
    }


    @Override
    public RepositoryVO findByUserIdDnet(String idUserDnet) {
/*        RepositoryVO repositoryVO = new RepositoryVO();
        List<RepositoryEntity> lista = repository.findByUserDnetId(idUserDnet);
        if (lista.size() > 0) {
            repositoryVO = RepositoryConverter.convertEntityToVO(lista.get(0));
        }
        return repositoryVO;*/
        return null;
    }

    @Override
    public RepositoryVO findByRepositoryIdDnet(String idRepositoryDnet) {

/*        RepositoryVO repositoryVO = new RepositoryVO();
        // busca en la tabla repository de la base de datos de la herramienta evaluador un repositorio con el id de dnet
        List<RepositoryEntity> lista = repository.findByDnetId(idRepositoryDnet);
        if (lista.size() > 0) {
            // si entra en esta condición, significa que hay un repositorio y vamos a retornarlo.
            return RepositoryConverter.convertEntityToVO(lista.get(0));


        }
        else {
            // si entra en esta condición, significa que  no existe en la base de datos de la herramienta evaluador y vamos a comprobar si existe en DNET
            try {
                List<RepositoryResponse> repositorios;
                repositorios = dnetService.getRepositoriesDNETByIdRepository(idRepositoryDnet);
                repositoryVO.setDNetId(idRepositoryDnet);
                repositoryVO.setnDeleteState(1);
                repositoryVO.setNombreDnet(repositorios.get(0).getOfficialName());
                return create(repositoryVO);
            }
            catch (Exception e) {
    
                e.printStackTrace();
            }
        }
        return repositoryVO;*/

        return null;
    }

    @Override
    public RepositoryVO findByUserId(Long idUser) {
        return RepositoryConverter.convertEntityToVO(repository.findRepositoriesByUserId(idUser).get(0));
    }

}

