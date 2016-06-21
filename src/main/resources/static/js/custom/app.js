/**
 * 客户端脚本入口
 * 
 */
var COOKIE_VAR_PREFIX = 'game_dist_';

 var App = function() {
     var handleInit = function() {
         !function ($) {
             $(document).on("click","ul.nav li.parent > a > span.icon", function(){
                 $(this).find('em:first').toggleClass("glyphicon-minus");
             }); 
             $(".sidebar span.icon").find('em:first').addClass("glyphicon-plus");
         }(window.jQuery);
         
         $(window).on('resize', function () {
             if ($(window).width() > 768) $('#sidebar-collapse').collapse('show')
         })
         $(window).on('resize', function () {
             if ($(window).width() <= 767) $('#sidebar-collapse').collapse('hide')
         })
     };
     var handleSwitches = function() {
         var switches = $('.bootstrap-switch');
         if ( switches && switches.length > 0 ) {
             $('.bootstrap-switch').bootstrapSwitch({
                 'onColor': 'success',
                 'size': 'small'
             });
        }
     };
     
     return {
         init: function() {
             handleInit();
             handleSwitches();
         },
         cookie: function(key, value) {
             if (value !== undefined && !$.isFunction(value)) {
                 $.cookie(COOKIE_VAR_PREFIX + key, value, { expires: 7, path: '/' });
             } else {
                 return $.cookie(COOKIE_VAR_PREFIX + key);
             }
         }
     };
 }();
 
 $(document).ready(function() {
     App.init();
 });