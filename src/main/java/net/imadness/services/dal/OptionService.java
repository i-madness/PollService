package net.imadness.services.dal;

import net.imadness.entities.Option;
import net.imadness.entities.Respondent;
import net.imadness.mappers.OptionMapper;
import net.imadness.mappers.extended.RespondentOptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OptionService {

    @Autowired
    private OptionMapper optionMapper;
    @Autowired
    private RespondentOptionMapper respondentOptionMapper;

    public void addOption(Option option) {
        optionMapper.addOption(option);
    }

    public Option getOptionById(long id) {
        Option result = optionMapper.getOptionById(id);
        if (result != null)
            result.setRespondents(respondentOptionMapper.getRespondentsForOption(id));
        return result;
    }

    public List<Option> getAllOptions() {
        List<Option> result = optionMapper.getAllOptions();
        if (result != null && result.size()>0) {
            for (Option option : result)
                option.setRespondents(respondentOptionMapper.getRespondentsForOption(option.getId()));
        }
        return result;
    }

    public void updateOption(Option option) {
        optionMapper.updateOption(option);
    }

    public void deleteOption(long id) {
        optionMapper.deleteOption(id);
    }

    public List<Option> getOptionsForRespondent(Respondent respondent) {
        return respondentOptionMapper.getOptionsForRespondent(respondent.getId());
    }

    public void insertRespondentOption(Long respondentId, Long optionId) {
        respondentOptionMapper.insertRespondentOption(respondentId,optionId);
    }

}