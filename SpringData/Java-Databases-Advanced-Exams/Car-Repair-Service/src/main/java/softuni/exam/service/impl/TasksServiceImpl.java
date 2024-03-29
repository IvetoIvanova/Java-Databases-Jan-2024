package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xmls.TaskRootDTO;
import softuni.exam.models.dto.xmls.TaskSeedDTO;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.models.entity.Task;
import softuni.exam.repository.MechanicsRepository;
import softuni.exam.repository.PartsRepository;
import softuni.exam.repository.TasksRepository;
import softuni.exam.service.TasksService;
import softuni.exam.util.ValidationUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TasksServiceImpl implements TasksService {
    private static String TASKS_FILE_PATH = "src/main/resources/files/xml/tasks.xml";
    private final TasksRepository taskRepository;
    private final MechanicsRepository mechanicsRepository;
    private final PartsRepository partsRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public TasksServiceImpl(TasksRepository taskRepository, MechanicsRepository mechanicsRepository, PartsRepository partsRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.taskRepository = taskRepository;
        this.mechanicsRepository = mechanicsRepository;
        this.partsRepository = partsRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.taskRepository.count() > 0;
    }

    @Override
    public String readTasksFileContent() throws IOException {
        return new String(Files.readAllBytes(Path.of(TASKS_FILE_PATH)));
    }

    @Override
    public String importTasks() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext jaxbContext = JAXBContext.newInstance(TaskRootDTO.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        TaskRootDTO taskRootDTO = (TaskRootDTO) unmarshaller.unmarshal(new File(TASKS_FILE_PATH));

        for (TaskSeedDTO taskSeedDTO : taskRootDTO.getTaskSeedDTOs()) {
            Optional<Mechanic> mechanicOptional = this.mechanicsRepository.findByFirstName(taskSeedDTO.getMechanic().getFirstName());

            if (!this.validationUtil.isValid(taskSeedDTO) || mechanicOptional.isEmpty()) {
                sb.append("Invalid task\n");
                continue;
            }

            Task task = this.modelMapper.map(taskSeedDTO, Task.class);
            task.setMechanic(mechanicOptional.get());
            this.taskRepository.saveAndFlush(task);

            sb.append(String.format("Successfully imported task %.2f\n",
                    task.getPrice()));
        }

        return sb.toString();
    }

    @Override
    public String getCoupeCarTasksOrderByPrice() {
        return this.taskRepository
                .findAllByCoupeCarTypeOOrderByPriceDesc()
                .stream()
                .map(t -> String.format("Car %s %s with %dkm\n" +
                                "-Mechanic: %s %s - task №%d:\n" +
                                " --Engine: %s\n" +
                                "---Price: %s$\n",
                        t.getCar().getCarMake(), t.getCar().getCarModel(), t.getCar().getKilometers(),
                        t.getMechanic().getFirstName(), t.getMechanic().getLastName(), t.getId(),
                        t.getCar().getEngine(), t.getPrice()))
                .collect(Collectors.joining());
    }
}
