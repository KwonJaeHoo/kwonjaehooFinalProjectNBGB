(function ($) {
  $(function () {
    // FAQ 제목 클릭 이벤트
    $('.js-ag-faq_title').on('click', function() {
      $(this).next('.js-ag-faq_sub_titles').slideToggle();
    });

    // FAQ 부제목 클릭 이벤트
    $('.js-ag-faq_sub_title').on('click', function() {
      $(this).next('.js-ag-faq_text').slideToggle();
    });
  });
})(jQuery);