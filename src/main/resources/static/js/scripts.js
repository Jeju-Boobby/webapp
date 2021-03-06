$(".answer-write input[type=submit]").on("click", addAnswer);

function addAnswer(e) {
    console.log("click");
    e.preventDefault();

    var queryString = $('.answer-write').serialize();
    console.log("query : " + queryString);

    var url = $('.answer-write').attr('action');
    console.log("url : " + url);
    $.ajax({
        type: 'post',
        url: url,
        data: queryString,
        dataType: 'json',
        error: onError,
        success: onSuccess
    });
}

function onError() {
    console.log("no");
}

function onSuccess(data, status) {
    console.log(data);
    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.writer.id, data.writer.userId, data.formattedCreateDate, data.contents, data.question.id, data.id);
    $(".qna-comment-slipp-articles").prepend(template);
    $("textarea[name=contents]").val("");
    commentCountPlus();
}

$(".qna-comment-slipp-articles").on("click", "a.link-delete-article", deleteAnswer);

function deleteAnswer(e) {
    e.preventDefault();
    var deleteBtn = $(this);

    var url = deleteBtn.attr("href");
    console.log(url);
    $.ajax({
        type: 'delete',
        url: url,
        dataType: 'json',
        error: function (xhr, status) {
            console.log("error");
        },
        success: function (data, status) {
            console.log(data);
            if (data.valid) {
                deleteBtn.closest("article").remove();
                commentCountMinus();
            } else {
                alert(data.errorMessage);
            }
        }
    });
}
function commentCountPlus() {
    var count = $(".qna-comment-count strong");

    count.text(parseInt(count.text()) + 1);
}
function commentCountMinus() {
    var count = $(".qna-comment-count strong");

    count.text(parseInt(count.text()) - 1);
}
String.prototype.format = function() {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function(match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};