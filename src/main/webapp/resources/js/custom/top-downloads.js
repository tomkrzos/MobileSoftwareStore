'use strict';

var TopDownloads = function () {
};

TopDownloads.prototype = function () {

    var topDownloadsDiv = $('#top-downloads'),
        topDownloadsLoader = $('#top-downloads-loader'),
        programsUrl = 'http://jquery-ui-project.esy.es/software_store/programs/',
        programDetailsUrl = '/details',
        restApiUrl = '/rest/top/get',
        errorMsg = 'Top downloads unavailable',
        timeout = 5000,
        fadeInMs = 500,

        init = function () {
            topDownloadsLoader.show();
            getJson()
                .done(function (data) {
                    topDownloadsDiv.html(generateHtml(data));
                    topDownloadsDiv.slick({
                        slidesToShow: 4,
                        slidesToScroll: 2,
                        dots: true
                    });
                })
                .fail(function () {
                    topDownloadsDiv.html(errorMsg);
                })
                .always(function () {
                    topDownloadsLoader.hide();
                });
        },

        generateHtml = function (data) {
            var html = '';
            $.each(data, function (index, program) {
                html += '<div>'
                    + '<a href="' + programDetailsUrl + '/' + program.id + '">'
                    + '<img src="' + programsUrl + encodeURIComponent(program.name) + '/' + encodeURIComponent(program.img512) + '">' + '<br/>'
                    + '</a>'
                    + 'Downloads: ' + program.downloads
                    + '</div>';
            });
            return html;
        },

        getJson = function () {
            return $.ajax({
                url: restApiUrl,
                dataType: "json",
                timeout: timeout
            });
        };

    return {
        init: init
    };

}();
