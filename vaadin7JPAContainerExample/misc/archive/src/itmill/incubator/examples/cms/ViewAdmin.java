package com.itmill.incubator.examples.cms;

import com.itmill.incubator.examples.cms.database.HbnContainer;
import com.itmill.incubator.examples.cms.database.HibernateUtil;
import com.itmill.incubator.examples.cms.database.HbnContainer.EntityItem;
import com.itmill.incubator.examples.cms.database.HbnContainer.SessionManager;
import com.itmill.incubator.examples.cms.entity.Certification;
import com.itmill.incubator.examples.cms.entity.Degree;
import com.itmill.incubator.examples.cms.entity.Person;
import com.itmill.incubator.examples.cms.entity.Project;
import com.itmill.incubator.examples.cms.entity.Publication;
import com.itmill.incubator.examples.cms.entity.Skill;
import com.vaadin.data.Container;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window.Notification;

@SuppressWarnings("serial")
public class ViewAdmin extends VerticalLayout implements ClickListener {

    private Button addButton;
    private Button refreshButton;
    private Button editButton;

    @SuppressWarnings("unchecked")
    private final Class c;
    private final SessionManager sess;
    private Table table;

    @SuppressWarnings("unchecked")
    public ViewAdmin(Class c, CMS cms) {
        this.c = c;
        sess = cms;
        buildView();
    }

    /**
     * Build the view required by the class
     */
    private void buildView() {
        addButton = new Button("Add a "
                + c.getName().substring(c.getName().lastIndexOf('.') + 1)
                        .toLowerCase(), this);
        refreshButton = new Button("Reload table", this);
        editButton = new Button("Edit row", this);

        setSizeFull();
        setSpacing(true);
        setMargin(true);

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSpacing(true);
        buttons.addComponent(addButton);
        buttons.addComponent(refreshButton);
        buttons.addComponent(editButton);
        buttons.setExpandRatio(editButton, 2);
        addComponent(buttons);

        table = new Table();
        table.setSizeFull();

        table.setSelectable(true);
        table.setImmediate(true);

        table.addActionHandler(new Handler() {
            Action edit = new Action("Edit...");
            Action delete = new Action("Delete");

            public Action[] getActions(Object target, Object sender) {
                return new Action[] { edit, delete };
            }

            public void handleAction(Action action, Object sender, Object target) {

                if (action == edit) {

                    HbnContainer cont = new HbnContainer(c, sess);

                    EntityItem item = (EntityItem) cont.getItem(target);

                    if (Skill.class.equals(c)) {
                        EditorSkill ed = new EditorSkill(
                                (Skill) item.getPojo(), "Edit skill",
                                ViewAdmin.this);
                        getWindow().addWindow(ed);
                    } else if (Project.class.equals(c)) {
                        EditorProject ed = new EditorProject((Project) item
                                .getPojo(), "Edit project", ViewAdmin.this);
                        getWindow().addWindow(ed);
                    } else if (Degree.class.equals(c)) {
                        EditorDegree ed = new EditorDegree((Degree) item
                                .getPojo(), "Edit Degree", ViewAdmin.this);
                        getWindow().addWindow(ed);
                    } else if (Publication.class.equals(c)) {
                        EditorPublication ed = new EditorPublication(
                                (Publication) item.getPojo(),
                                "Edit Publication", ViewAdmin.this);
                        getWindow().addWindow(ed);
                    } else if (Certification.class.equals(c)) {
                        EditorCertification ed = new EditorCertification(
                                (Certification) item.getPojo(),
                                "Edit Certification", ViewAdmin.this);
                        getWindow().addWindow(ed);
                    } else if (Person.class.equals(c)) {
                        EditorPerson ed = new EditorPerson((Person) item
                                .getPojo(), "Edit Person", ViewAdmin.this, sess);
                        getWindow().addWindow(ed);
                    }
                } else if (action == delete) {
                    HbnContainer cont = new HbnContainer(c, sess);
                    EntityItem item = (EntityItem) cont.getItem(target);
                    HibernateUtil.deleteObject(item.getPojo());
                    loadEntries();
                }
            }
        });

        addComponent(table);
        setExpandRatio(table, 1.0f);
    }

    /**
     * Reload data of the passed object from database into table
     */
    public void loadEntries() {
        Container cont = new HbnContainer(c, sess);
        table.setContainerDataSource(cont);

        if (Person.class.equals(c)) {
            table.setColumnCollapsingAllowed(true);

            try {
                table.setColumnHeader("birthDate", "Birthdate");
                table.setColumnHeader("cellphone", "Cellphone Number");
                table.setColumnHeader("email", "E-mail");
                table.setColumnHeader("name", "Full Name");
                table.setColumnHeader("nationality", "Nationality");
                table.setColumnHeader("degrees", "Degrees");
                table.setColumnHeader("jobs", "Jobs");
                table.setColumnHeader("projects", "Projects");
                table.setColumnHeader("publications", "Publications");
                table.setColumnHeader("certifications", "Certifications");
                table.setColumnHeader("skills", "Skills");

                table.setColumnCollapsed("degrees", true);
                table.setColumnCollapsed("jobs", true);
                table.setColumnCollapsed("publications", true);
                table.setColumnCollapsed("certifications", true);
                table.setColumnCollapsed("skills", true);
                table.setColumnCollapsed("projects", true);
            } catch (IllegalAccessException e) {
                System.out.println(e);
            }
        } else if (Degree.class.equals(c)) {
            table.setColumnHeader("title", "Degree");
            table.setColumnHeader("school", "Institution");
            table.setVisibleColumns(new Object[] { "title", "school" });
        } else if (Project.class.equals(c)) {
            table.setColumnHeader("title", "Title");
            table.setColumnHeader("description", "Description");
            table.setVisibleColumns(new Object[] { "title", "description" });
            table.setColumnWidth("description", 500);
        } else if (Publication.class.equals(c)) {
            table.setColumnHeader("title", "Publication Title");
        } else if (Skill.class.equals(c)) {
            table.setColumnHeader("name", "Skill");
            table.setColumnHeader("grouping", "Skill Group");
            table.setVisibleColumns(new Object[] { "name", "grouping" });
        }
    }

    /**
     * Handle buttons
     */
    public void buttonClick(ClickEvent event) {
        if (event.getButton() == addButton) {
            if (Skill.class.equals(c)) {
                EditorSkill ed = new EditorSkill(new Skill(), "Add a skill",
                        this);
                getWindow().addWindow(ed);
            } else if (Project.class.equals(c)) {
                EditorProject ed = new EditorProject(new Project(),
                        "Add a project", this);
                getWindow().addWindow(ed);
            } else if (Degree.class.equals(c)) {
                EditorDegree ed = new EditorDegree(new Degree(),
                        "Add a degree", this);
                getWindow().addWindow(ed);
            } else if (Publication.class.equals(c)) {
                EditorPublication ed = new EditorPublication(new Publication(),
                        "Add a publication", this);
                getWindow().addWindow(ed);
            } else if (Certification.class.equals(c)) {
                EditorCertification ed = new EditorCertification(
                        new Certification(), "Add a certification", this);
                getWindow().addWindow(ed);
            } else if (Person.class.equals(c)) {
                EditorPerson ed = new EditorPerson(new Person(),
                        "Add a person", this, sess);
                getWindow().addWindow(ed);
            }

        } else if (event.getButton() == refreshButton) {
            loadEntries();
        } else if (event.getButton() == editButton) {
            if (table.getValue() == null) {
                getWindow().showNotification("Select a row to edit",
                        Notification.TYPE_WARNING_MESSAGE);
                return;
            }

            HbnContainer cont = new HbnContainer(c, sess);
            EntityItem item = (EntityItem) cont.getItem(table.getValue());

            if (Skill.class.equals(c)) {
                EditorSkill ed = new EditorSkill((Skill) item.getPojo(),
                        "Edit skill", this);
                getWindow().addWindow(ed);
            } else if (Project.class.equals(c)) {
                EditorProject ed = new EditorProject((Project) item.getPojo(),
                        "Edit project", this);
                getWindow().addWindow(ed);
            } else if (Degree.class.equals(c)) {
                EditorDegree ed = new EditorDegree((Degree) item.getPojo(),
                        "Edit Degree", this);
                getWindow().addWindow(ed);
            } else if (Publication.class.equals(c)) {
                EditorPublication ed = new EditorPublication((Publication) item
                        .getPojo(), "Edit Publication", this);
                getWindow().addWindow(ed);
            } else if (Certification.class.equals(c)) {
                EditorCertification ed = new EditorCertification(
                        (Certification) item.getPojo(), "Edit Certification",
                        this);
                getWindow().addWindow(ed);
            } else if (Person.class.equals(c)) {
                EditorPerson ed = new EditorPerson((Person) item.getPojo(),
                        "Edit Person", this, sess);
                getWindow().addWindow(ed);
            }
        }
    }
}