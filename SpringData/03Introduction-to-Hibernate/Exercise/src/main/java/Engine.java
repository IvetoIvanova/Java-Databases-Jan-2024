import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Engine implements Runnable {

    private final EntityManager entityManager;
    private BufferedReader bufferedReader;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        System.out.println("Select exercise number: ");

        try {
            int exerciseNumber = Integer.parseInt(bufferedReader.readLine());

            switch (exerciseNumber) {
                case 2:
                    changeCasingExercise2();
                    break;
                case 3:
                    containsEmployeeExercise3();
                    break;
                case 4:
                    employeesWithASalaryOver50000Exercise4();
                    break;
                case 5:
                    employeesFromDepartmentExercise5();
                    break;
                case 6:
                    addingANewAddressAndUpdatingTheEmployee();
                    break;
                case 7:
                    addressesWithEmployeeCount();
                    break;
                case 8:
                    getEmployeesWithProject();
                    break;
                case 9:
                    findTheLatest10Projects();
                    break;
                case 10:
                    increaseSalaries();
                    break;
                case 11:
                    findEmployeesByFirstName();
                    break;
                case 12:
                    employeesMaximumSalaries();
                    break;
                case 13:
                    removeTowns();
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    private void removeTowns() throws IOException {
        System.out.println("Enter town name:");
        String townName = bufferedReader.readLine();

        List<Town> resultList = entityManager.createQuery("FROM Town WHERE name = :name", Town.class)
                .setParameter("name", townName)
                .getResultList();

        if (!resultList.isEmpty()) {
            Town town = resultList.get(0);
            List<Address> addresses = entityManager.createQuery("SELECT a FROM Address a JOIN a.town t WHERE t.name = :t_name",
                            Address.class)
                    .setParameter("t_name", town.getName())
                    .getResultList();

            addresses.forEach(a -> {
                a.getEmployees().forEach(e -> {
                    e.setAddress(null);
                    entityManager.persist(e);
                });
                entityManager.getTransaction().begin();
                entityManager.remove(a);
                entityManager.getTransaction().commit();
            });

            System.out.printf("%s address in %s deleted",
                    addresses.size(), town.getName());
            entityManager.getTransaction().begin();
            entityManager.remove(town);
            entityManager.getTransaction().commit();
        }
    }

    private void employeesMaximumSalaries() {
        String fromDepartment = entityManager.createQuery("FROM Department ", Department.class)
                .getResultStream()
                .map(d -> {
                    double max = d.getEmployees().stream()
                            .mapToDouble(e -> e.getSalary().doubleValue())
                            .max()
                            .orElse(0);

                    if (max < 30000 || max > 70000) {
                        return String.format("%s %.2f", d.getName(), max);
                    } else {
                        return "";
                    }
                })
                .filter(d -> !d.isEmpty())
                .collect(Collectors.joining("\n"));

        System.out.println(fromDepartment);

//        List<Object[]> rows = entityManager
//                .createNamedQuery("SELECT d.name, MAX(e.salary) AS 'm_salary' FROM departments d " +
//                        "JOIN employees e ON d.department_id = e.department_id " +
//                        "GROUP BY d.name " +
//                        "HAVING 'm_salary' NOT BETWEEN 30000 AND 70000")
//                .getResultList();
    }

    private void findEmployeesByFirstName() throws IOException {
        String letters = bufferedReader.readLine();
        entityManager.createQuery("FROM Employee WHERE firstName LIKE CONCAT(:letters, '%')", Employee.class)
                .setParameter("letters", letters)
                .getResultStream()
                .forEach(e -> System.out.printf("%s %s - %s - ($%.2f)%n",
                        e.getFirstName(), e.getLastName(), e.getJobTitle(), e.getSalary()));
    }

    private void increaseSalaries() {
        entityManager.getTransaction().begin();
        int affectedRows = entityManager.createQuery("UPDATE Employee e " +
                        "SET e.salary = e.salary * 1.12 " +
                        "WHERE e.department.id IN :ids")
                .setParameter("ids", Set.of(1, 2, 4, 11))
                .executeUpdate();

        entityManager.getTransaction().commit();
        System.out.println(affectedRows);

        List<Employee> employees = entityManager.createQuery("SELECT e FROM Employee e JOIN e.department d " +
                "WHERE d.name IN ('Engineering', 'Tool Design', 'Marketing', 'Information Services')", Employee.class).getResultList();
        for (Employee employee : employees) {
            System.out.printf("%s %s ($%.2f)%n",
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getSalary());
        }

    }

    private void findTheLatest10Projects() {
        entityManager.createQuery("FROM Project ORDER BY startDate DESC", Project.class)
                .setMaxResults(10)
                .getResultStream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(p -> System.out.printf("Project name: %s%n" +
                                "\tProject Description: %s%n" +
                                "\tProject Start Date:%s%n" +
                                "\tProject End Date: %s%n",
                        p.getName(), p.getDescription(), p.getStartDate(), p.getEndDate()));
    }

    private void getEmployeesWithProject() throws IOException {
        System.out.println("Enter employee id:");
        Integer employeeId = Integer.valueOf(bufferedReader.readLine());

        Employee employee = entityManager.find(Employee.class, employeeId);
        System.out.printf("%s %s - %s%n",
                employee.getFirstName(),
                employee.getLastName(),
                employee.getJobTitle());

        employee.getProjects().stream().sorted(Comparator.comparing(Project::getName))
                .forEach(p -> System.out.printf("\t%s%n", p.getName()));
    }

    private void addressesWithEmployeeCount() {
        List<Address> addresses = entityManager.createQuery("SELECT a FROM Address a " +
                        "ORDER BY a.employees.size DESC", Address.class)
                .setMaxResults(10)
                .getResultList();

        addresses.forEach(address -> {
            System.out.printf("%s, %s - %d employees%n",
                    address.getText(),
                    address.getTown() == null
                            ? "Unknown" : address.getTown().getName(),
                    address.getEmployees().size());
        });
    }

    private void addingANewAddressAndUpdatingTheEmployee() throws IOException {
        System.out.println("Enter employee last name:");
        String lastName = bufferedReader.readLine();

        Employee employee = entityManager.createQuery("SELECT e FROM Employee e " +
                        "WHERE e.lastName = :l_name", Employee.class)
                .setParameter("l_name", lastName)
                .getSingleResult();

        Address address = createAddress("Vitoshka 15");

        entityManager.getTransaction().begin();
        employee.setAddress(address);
        entityManager.getTransaction().commit();
    }

    private Address createAddress(String addressText) {
        Address address = new Address();
        address.setText(addressText);

        entityManager.getTransaction().begin();
        entityManager.persist(address);
        entityManager.getTransaction().commit();

        return address;
    }

    private void employeesFromDepartmentExercise5() {
        entityManager.createQuery("SELECT e FROM Employee e " +
                        "WHERE e.department.name = :d_name " +
                        "ORDER BY e.salary, e.id", Employee.class)
                .setParameter("d_name", "Research and Development")
                .getResultStream()
                .forEach(employee -> {
                    System.out.printf("%s %s from %s - $%.2f%n",
                            employee.getFirstName(),
                            employee.getLastName(),
                            employee.getDepartment().getName(),
                            employee.getSalary());
                });
    }

    private void employeesWithASalaryOver50000Exercise4() {
        entityManager.createQuery("SELECT e FROM Employee e " +
                        "WHERE e.salary > :min_salary", Employee.class)
                .setParameter("min_salary", BigDecimal.valueOf(50000L))
                .getResultStream()
                .map(Employee::getFirstName)
                .forEach(System.out::println);
    }

    private void containsEmployeeExercise3() throws IOException {
        System.out.println("Enter employee full name:");
        String[] fullName = bufferedReader.readLine().split("\\s+");
        String firstName = fullName[0];
        String lastName = fullName[1];

        Long singleResult = entityManager.createQuery("SELECT count(e) FROM Employee e " +
                                "WHERE e.firstName = :f_name AND e.lastName = :l_name",
                        Long.class)
                .setParameter("f_name", firstName)
                .setParameter("l_name", lastName)
                .getSingleResult();

        System.out.println(singleResult == 0
                ? "No" : "Yes");
    }

    private void changeCasingExercise2() {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("UPDATE Town t " +
                "SET t.name = upper(t.name) " +
                "WHERE length(t.name) <= 5 ");
        System.out.println(query.executeUpdate());

        entityManager.getTransaction().commit();
    }
}
