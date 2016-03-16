(function ($) {
    $(function () {
        var tfooter = document.createElement('div')
        tfooter.className = 'tfootData';

        $.widget("zpd.paging", {
            options: {
                limit: 10,
                rowDisplayStyle: 'block',
                activePage: 0,
                rows: [], currentPage: 1
            },
            _create: function () {
                var rows = $("tbody", this.element).children();
                this.options.rows = rows;
                this.options.rowDisplayStyle = rows.css('display');
                var nav = this._getNavBar();
                this.element.after(nav);
                this.element.after(tfooter);
                this.showPage(0);
            },
            _getNavBar: function () {
                var rows = this.options.rows;
                var totalPages = Math.ceil(rows.length / this.options.limit);
                var lastPage = totalPages - 1;
                var nav = $('<div>', {class: 'paging-nav'});

                for (var i = 0; i < totalPages; i++) {

                    //btn 1 active on page load
                    if (i === 0) {
                        tfooter.innerHTML = 'PAGE 1/'+totalPages+' [1 - 10 of ' + rows.length + ' ENTRIES]';
                        this._on($('<a href="#" class="selected-page" data-page="0" value="1" totalPages="' + totalPages + '">1</a>', {
                        }).appendTo(nav),
                                {click: "pageClickHandler"});

                    } else {
                        this._on($('<a>', {
                            href: '#',
                            text: (i + 1),
                            "data-page": (i),
                            "value": (i + 1),
                            "totalPages": totalPages,
                        }).appendTo(nav),
                                {click: "pageClickHandler"});
                    }
                }

                //create previous link
                this._on($('<a>', {
                    href: '#',
                    text: '<<',
                    "data-direction": -1,
                    "value": (i + 1),
                    "totalPages": totalPages,
                }).prependTo(nav),
                        {click: "pageStepHandler"});
                //first
                this._on($('<a>', {
                    href: '#',
                    text: 'FIRST',
                    "data-page": (0),
                    "value": 1,
                    "totalPages": totalPages,
                }).prependTo(nav),
                        {click: "pageClickHandler"});
                //create next link
                this._on($('<a>', {
                    href: '#',
                    text: '>>',
                    "data-direction": +1,
                    "value": (i + 1),
                    "totalPages": totalPages,
                }).appendTo(nav),
                        {click: "pageStepHandler"});
                //last
                this._on($('<a>', {
                    href: '#',
                    text: 'LAST',
                    "data-page": (lastPage),
                    "value": lastPage + 1,
                    "totalPages": totalPages,
                }).appendTo(nav),
                        {click: "pageClickHandler"});

                return nav;
            },
            showPage: function (pageNum) {
                var num = pageNum * 1; //it has to be numeric
                this.options.activePage = num;
                var rows = this.options.rows;
                var limit = this.options.limit;
                for (var i = 0; i < rows.length; i++) {
                    if (i >= limit * num && i < limit * (num + 1)) {
                        $(rows[i]).css('display', this.options.rowDisplayStyle);
                    } else {
                        $(rows[i]).css('display', 'none');
                    }
                }
            },
            pageClickHandler: function (event) {
                event.preventDefault();
                var totalRecords = this.options.rows;
                var currentPage = $(event.target).attr('value');
                var from = ((currentPage - 1) * this.options.limit) + 1;
                var to = from + this.options.limit - 1;
                var pageNum = $(event.target).attr('data-page');
                var totalNoOfPages = $(event.target).attr('totalPages');
               
                $(event.target).siblings().attr('class', "");
                $(event.target).attr('class', "selected-page");
                if (to > totalRecords.length) {
                    to = totalRecords.length;
                }
                tfooter.innerHTML = 'PAGE '+ currentPage + '/'+ totalNoOfPages +' ['+from + ' - ' + to + ' OF ' + totalRecords.length + ' ENTRIES]';
                this.showPage(pageNum);
            },
            pageStepHandler: function (event) {
                event.preventDefault();
                //get the direction and ensure it's numeric
                var dir = $(event.target).attr('data-direction') * 1;
                var pageNum = this.options.activePage + dir;
                //if we're in limit, trigger the requested pages link
                if (pageNum >= 0 && pageNum < this.options.rows.length) {
                    $("a[data-page=" + pageNum + "]", $(event.target).parent()).click();
                }
            }
        });
    });
})(jQuery);



