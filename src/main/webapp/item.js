$(document).ready(function () {
    initPage();
    $('#create-item').click(function () {
        $('#new-item').modal('show');
        createItem();

        $('#close-item').off().click(function () {
            let desc = $(this).parent().parent().find('#description').val();
            let status = $(this).parent().parent().find("#create-ch").prop('checked');
            if (desc !== '') {
                saveItem(desc, status)
            }
        });
    });

    $('.check :checkbox').change(function () {
        if ($("#show-all").prop('checked')) {
            showTasks('/get-all');
        } else {
            showTasks('/get-active');
        }
    });
});

function initPage() {
    if ($("#show-all").prop('checked')) {
        showTasks('/get-all');
    } else {
        showTasks('/get-active');
    }
}

function createItem() {
    $('.item-content .item-body').remove();
    $('.item-header').after("<div class='modal-body item-body'>");
    $('.item-body').append(
        "<form>"
        + "<div class='form-group has-feedback login-valid'>"
        + "<label for='description'>Опишите задание: </label>"
        + "<textarea type='text' rows='5'  name='description' class='form-control' id='description' value=''></textarea>"
        + "</div>"
        + "<label class='checkbox-inline create-checkbox'><input type='checkbox' id='create-ch' value=''>"
        + "Пометить как выполненное</label>"
        + "</form>"
    );
}

function saveItem(description, status) {
    $.ajax({
        url: '/save',
        type: 'POST',
        data: {desc: description, done: status},
        dataType: 'text',
    }).done(function () {
        initPage();
        $('#new-item').modal('hide');
    }).fail(function (err) {
        console.log(err);
    });
}

function showTasks(url) {
    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'text',
    }).done(function (list) {
        list = JSON.parse(list);
        $('.list-todo tr').remove();
        $(list).each(function (index, user) {
            let status = '';
            let date = formatDate(new Date(user.created));
            if (user.done) {
                status = "<td>Выполнено</td>"
            } else {
                status = "<td>Не выполнено</td>"
            }
            $('.list-todo').append("<tr>"
                + "<td>" + user.id + "</td>"
                + "<td>" + user.description + "</td>"
                + "<td>" + date + "</td>"
                + status
                + "<td><input type='button' class='btn btn-info btn-sm' id='update-item' value='Обновить задание'></td>"
                + "<td><input type='button' class='btn btn-danger btn-sm' id='delete-item' value='Удалить задание'></td>"
                + "</tr>").on('click', '#delete-item', function (event) {
                let idForDelete = $(this).parent().parent()[0].cells[0].innerText;
                deleteItem(idForDelete);
                event.stopImmediatePropagation();
                return false;
            }).on('click', '#update-item', function (event) {
                $('#show-update-item').modal('show');
                let description = $(this).parent().parent()[0].cells[1].innerText;
                let id = $(this).parent().parent()[0].cells[0].innerText;
                updateItem(description, id);
                event.stopImmediatePropagation();
                return false;
            })
        });
    }).fail(function (err) {
        console.log(err);
    });
}

function updateItem(description, updateId) {
    let id = updateId;
    $('.update-item-content .update-item-body').remove();
    $('.update-item-header').after("<div class='modal-body update-item-body'>");
    $('.update-item-body').append(
        "<form>"
        + "<div class='form-group'>"
        + "<label for='update-description'>Опишите задание: </label>"
        + "<textarea type='text' rows='5'  name='update-description' class='form-control' id='update-description' value=''>"
        + description + "</textarea>"
        + "</div>"
        + "<label class='checkbox-inline update-checkbox'><input type='checkbox' id='update-ch' value=''>"
        + "Пометить как выполненное</label>"
        + "</form>"
    );

    $('#close-update-item').off().click(function () {
        let desc = $('#update-description').val();
        let status = $('#update-ch').prop('checked');
        if (desc !== '' && id !== '') {
            $.ajax({
                url: '/update-item',
                type: 'POST',
                data: {id: id, desc: desc, done: status},
                dataType: 'text',
            }).done(function () {
                initPage();
                $('#show-update-item').modal('hide');
            }).fail(function (err) {
                console.log(err);
            });
        }
    })
}

function deleteItem(id) {
    $.ajax({
        url: '/delete-item',
        type: 'POST',
        data: {id: id},
        dataType: 'text',
    }).done(function () {
        initPage();
    }).fail(function (err) {
        console.log(err);
    });
}

function formatDate(d) {
    return ("00" + (d.getMonth() + 1)).slice(-2)
        + "-" + ("00" + d.getDate()).slice(-2)
        + "-" + d.getFullYear() + " "
        + ("00" + d.getHours()).slice(-2) + ":"
        + ("00" + d.getMinutes()).slice(-2)
        + ":" + ("00" + d.getSeconds()).slice(-2);
}