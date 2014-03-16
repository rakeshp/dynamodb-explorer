/**
 * Created by rakesh on 12/03/14.
 */

$('#describe').click(function() {
    var tableName = $('#table').val();
    $.post('/table/describe', {name: tableName}, function(data) {
        $('#data').JSONView(data);
    });
});

$('#scan').click(function() {
    var tableName = $('#table').val();
    $.post('/table/scan', {name: tableName}, function(data) {
        $('#data').JSONView(data);
    });
});

$('#get').click(function() {
    var tableName = $('#table').val();
    var hash = $('input#item-hash').val();
    var range = $('input#item-range').val();
    $.post('/table/get-item', {name: tableName, hash: hash, range: range},
        function(data) {
        $('#data').JSONView(data, {collapsed: true});
    });
});