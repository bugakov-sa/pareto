var getNewRecordTextArea = function() {
    return $('#new-record-collapse > div > textarea');
}

var getRecordPanel = function(recordId) {
    return $('#' + recordId);
}

var closeNewRecordMode = function() {
    $('#new-record-collapse').removeClass('collapse in').addClass('collapse');
    getNewRecordTextArea().val('');
}

var cancelNewRecord = function() {
    closeNewRecordMode();
}

var saveNewRecord = function() {
    var newText = getNewRecordTextArea().val();
    var requestBody = { 'text': newText }
    $.ajax({
        url: "/rawrecord/",
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(requestBody),
        dataType: 'json',
        success: function(record){
            $("#records-panel-group").prepend(buildRecordPanel(record));
            closeNewRecordMode();
        }
    });
}

var editRecord = function(recordId){
    $('#' + recordId + ' > div > .show-mode').hide();
    $('#' + recordId + ' > div > .edit-mode').show();
    var recordEditArea = $('#' + recordId + ' > div > textarea');
    $.getJSON("/rawrecord/" + recordId, function(data, status){
        recordEditArea.val(data.text);
    });
    recordEditArea.show();
}

var closeEditMode = function(recordId) {
    var recordEditArea = $('#' + recordId + ' > div > textarea');
    recordEditArea.hide();
    recordEditArea.val('');
    $('#' + recordId + ' > div > .edit-mode').hide();
    $('#' + recordId + ' > div > .show-mode').show();
}

var saveEditRecord = function(recordId) {
    var newText = $('#' + recordId + ' > div > textarea').val();
    var requestBody = { 'text': newText }
    $.ajax({
        url: "/rawrecord/" + recordId,
        method: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(requestBody),
        dataType: 'json',
        success: function(record){
            var recordContentDiv = $('#' + recordId + ' > div.panel-body > div');
            recordContentDiv.html('');
            fillRecordContent(recordContentDiv, record);
            closeEditMode(record.id);
        }
    });
}

var cancelEditRecord = function(recordId) {
    closeEditMode(recordId);
}

var removeRecord = function(recordId){
    $.ajax({
        url: "/rawrecord/" + recordId,
        method: 'DELETE',
        contentType: 'application/json',
        success: function(){
            getRecordPanel(recordId).remove();
        }
    });
}

var buildRecordPanelHeader = function(record) {
    return $('<div class="panel-heading"></div>').text(record.date);
}

var fillRecordContent = function(recordContentDiv, record) {
    for(i in record.rows) {
        var row = record.rows[i]
        switch(row.rowType) {
            case "metrics":
            case "header":
                recordContentDiv.append($('<h5></h5>').text(row.rowText));
                break;
            case "paragraph":
                recordContentDiv.append($('<p class="text-justify"></p>').text(row.rowText));
                break;
        }
    }
}

var buildRecordPanelBody = function(record) {
    var recordBody = $('<div class="panel-body"></div>');
    var recordContentDiv = $('<div></div>');
    fillRecordContent(recordContentDiv, record);
    recordBody.append(recordContentDiv);
    var recordEditArea = $('<textarea class="form-control" rows="5"></textarea>');
    recordEditArea.hide()
    recordBody.append(recordEditArea);
    return recordBody;
}

var buildRecordPanelFooter = function(record) {
    var recordEditButton = $('<button type="button" class="btn btn-default"></button>');
    recordEditButton.text('Изменить')
    recordEditButton.click(function(){
        editRecord(record.id);
    });
    var recordRemoveButton = $('<button type="button" class="btn btn-default"></button>');
    recordRemoveButton.text('Удалить')
    recordRemoveButton.click(function(){
        removeRecord(record.id);
    });
    var showModeButtons = $('<div class="btn-group show-mode"></div>');
    showModeButtons.append(recordEditButton, recordRemoveButton);

    var recordSaveEditButton = $('<button type="button" class="btn btn-default"></button>');
    recordSaveEditButton.text('Сохранить')
    recordSaveEditButton.click(function(){
        saveEditRecord(record.id);
    });
    var recordCancelEditButton = $('<button type="button" class="btn btn-default"></button>');
    recordCancelEditButton.text('Отменить')
    recordCancelEditButton.click(function(){
        cancelEditRecord(record.id);
    });
    var editModeButtons = $('<div class="btn-group edit-mode"></div>');
    editModeButtons.append(recordSaveEditButton, recordCancelEditButton);
    editModeButtons.hide();

    var recordFooter = $('<div class="panel-footer" align="right"></div>');
    recordFooter.append(showModeButtons);
    recordFooter.append(editModeButtons);
    return recordFooter;
}

var buildRecordPanel = function(record) {
    var recordPanel = $('<div id="' + record.id + '" class="panel panel-default"></div>');
    recordPanel.append(buildRecordPanelHeader(record));
    recordPanel.append(buildRecordPanelBody(record));
    recordPanel.append(buildRecordPanelFooter(record));
    return recordPanel;
}

var switchToRecords = function() {
    $('#records').show();
    $('#records-href').addClass('active');
    $('#reports').hide();
    $('#reports-href').removeClass('active');
}

var switchToReports = function() {
    $('#records').hide();
    $('#records-href').removeClass('active');
    $('#reports').show();
    $('#reports-href').addClass('active');
}

$(document).ready(function(){
    $.getJSON("/record/", function(data, status) {
        for(i in data) {
            $("#records-panel-group").append(buildRecordPanel(data[i]));
        }
    });
    $('#save-new-record-button').click(saveNewRecord);
    $('#cancel-new-record-button').click(cancelNewRecord);
    $('#records-href').click(switchToRecords);
    $('#reports-href').click(switchToReports);
    switchToRecords();
});