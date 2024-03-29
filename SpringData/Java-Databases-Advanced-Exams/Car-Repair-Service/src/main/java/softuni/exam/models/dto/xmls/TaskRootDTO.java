package softuni.exam.models.dto.xmls;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "tasks")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskRootDTO implements Serializable {

    @XmlElement(name = "task")
    private List<TaskSeedDTO> taskSeedDTOs;

    public List<TaskSeedDTO> getTaskSeedDTOs() {
        return taskSeedDTOs;
    }

    public void setTaskSeedDTOs(List<TaskSeedDTO> taskSeedDTOs) {
        this.taskSeedDTOs = taskSeedDTOs;
    }
}
