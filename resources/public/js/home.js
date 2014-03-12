/**
 * Created by rakesh on 12/03/14.
 */

$('#describe').click(function() {
    var tableName = $('#table').val();
    $.post('/table/describe', {name: tableName}, function(data) {
        $('#data').JSONView(data);
    });
});

$('#get').click(function() {
    var tableName = $('#table').val();
    var id = $('input#item-id').val();
    $.post('/table/get-item', {name: tableName, id: id}, function(data) {
        $('#data').JSONView(data);
    });
});