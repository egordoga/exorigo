package ex.wicket;

import ex.dto.UserDto;
import ex.entity.User;
import ex.service.UserService;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserPage extends WebPage {

    @SpringBean
    private UserService userService;

    private String selected = "First name";
    private ListView<UserDto> userListView;
    private ListView<UserDto> userListUpdate;
    private List<UserDto> usersSelected = new ArrayList<>();
    private List<UserDto> usersDto = fillDto(userService.findUsers());
    private Form formView;
    private Form formUpdate;
    private Form formSearch;
    private boolean del = false;
    private CheckBox delete;
    private AjaxLink linkAll;

    public UserPage() {

        formView = new Form<UserDto>("formView") {
            @Override
            protected void onSubmit() {
                List<UserDto> list = userListView.getModelObject();
                List<UserDto> listRemove = new ArrayList<>();

                if (del) {
                    for (UserDto userDto : list) {
                        if (userDto.isSelected()) {
                            userService.deleteUser(userDto.getId());
                            listRemove.add(userDto);
                        }
                    }
                    list.removeAll(listRemove);
                    userListView.setDefaultModelObject(list);
                } else {
                    usersSelected.clear();
                    for (UserDto userDto : list) {
                        if (userDto.isSelected()) {
                            usersSelected.add(userDto);
                        }
                    }
                    changeVisible();
                }
            }

        };
        formUpdate = new Form<UserDto>("formUpdate") {
            @Override
            protected void onSubmit() {
                for (Component comp : userListUpdate) {
                    UserDto userDto = (UserDto) comp.getDefaultModelObject();
                    User user = userService.findUser(userDto.getId());
                    user.setName(userDto.getName());
                    user.setLastName(userDto.getLastName());
                    userService.updateUser(user);
                }
                changeVisible();
            }
        };
        userListView = new ListView<UserDto>("users", usersDto) {
            @Override
            protected void populateItem(ListItem<UserDto> listItem) {
                UserDto userDto = listItem.getModelObject();
                listItem.add(new Label("id", new PropertyModel(listItem.getModel(), "id")));
                listItem.add(new Label("name", new PropertyModel(listItem.getModel(), "name")));
                listItem.add(new Label("lastName", new PropertyModel(listItem.getModel(), "lastName")));
                listItem.add(new CheckBox("check", new PropertyModel<>(userDto, "isSelected")));
            }
        };
        userListView.setOutputMarkupId(true);
        formView.add(userListView);

        userListUpdate = new ListView<UserDto>("users", usersSelected) {
            @Override
            protected void populateItem(ListItem<UserDto> listItem) {
                listItem.add(new Label("id", new PropertyModel(listItem.getModel(), "id")));
                listItem.add(new TextField<>("name", new PropertyModel<>(listItem.getModelObject(), "name")));
                listItem.add(new TextField<>("lastName", new PropertyModel<>(listItem.getModelObject(), "lastName")));
            }
        };
        userListUpdate.setOutputMarkupId(true);
        formUpdate.add(userListUpdate);


        delete = new CheckBox("del", new PropertyModel<>(this, "del"));
        formView.add(delete);

        final WebMarkupContainer listContainer = new WebMarkupContainer("container");
        listContainer.add(userListView);
        listContainer.setOutputMarkupId(true);
        formView.add(listContainer);

        formSearch = new Form("userForm");
        final TextField<String> search = new TextField<>("search", Model.of(""));
        List<String> searchType = Arrays.asList("First name", "Last name");
        RadioChoice<String> radioGroup = new RadioChoice<>("radioGroup", new PropertyModel<>(this, "selected"), searchType);

        AjaxSubmitLink linkSearch = new AjaxSubmitLink("searchLink") {
            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                if ("First name".equals(selected)) {
                    usersDto = fillDto(userService.findUsersByName(search.getDefaultModelObjectAsString()));
                } else {
                    usersDto = fillDto(userService.findUsersByLastName(search.getDefaultModelObjectAsString()));
                }
                userListView.setList(usersDto);
                target.add(listContainer);
            }
        };
        linkAll = new AjaxLink<String>("linkAll") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                usersDto = fillDto(userService.findUsers());
                userListView.setList(usersDto);
                target.add(listContainer);
            }
        };

        formSearch.setVisible(true);
        formView.setVisible(true);
        formUpdate.setVisible(false);
        linkAll.setVisible(true);

        formSearch.add(search);
        formSearch.add(radioGroup);
        formSearch.add(linkSearch);
        add(linkAll);
        add(formSearch);
        add(formUpdate);
        add(formView);
    }

    private void changeVisible() {
        formUpdate.setVisible(!formUpdate.isVisible());
        formView.setVisible(!formView.isVisible());
        linkAll.setVisible(!linkAll.isVisible());
        formSearch.setVisible(!formSearch.isVisible());
    }

    private List<UserDto> fillDto(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();

        for (User user : users) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setLastName(user.getLastName());
            userDto.setSelected(Boolean.FALSE);
            userDtos.add(userDto);
        }

        return userDtos;
    }
}
