$(function() {

	function t(t) {
		var e = 0;
		return $(t).each(function() {
				e += $(this).outerWidth(!0)
			}),
			e
	}

	function e(e) {
		var a = t($(e).prevAll()),
			i = t($(e).nextAll()),
			n = t($(".content-tabs").children().not(".J_menuTabs")),
			s = $(".content-tabs").outerWidth(!0) - n,
			r = 0;
		if ($(".page-tabs-content").outerWidth() < s)
			r = 0;
		else if (i <= s - $(e).outerWidth(!0) - $(e).next().outerWidth(!0)) {
			if (s - $(e).next().outerWidth(!0) > i) {
				r = a;
				for (var o = e; r - $(o).outerWidth() > $(".page-tabs-content").outerWidth() - s;)
					r -= $(o).prev().outerWidth(),
					o = $(o).prev()
			}
		} else
			a > s - $(e).outerWidth(!0) - $(e).prev().outerWidth(!0) && (r = a - $(e).prev().outerWidth(!0));
		$(".page-tabs-content").animate({
			marginLeft: 0 - r + "px"
		}, "0");
	}

	function a() {
		var e = Math.abs(parseInt($(".page-tabs-content").css("margin-left"))),
			a = t($(".content-tabs").children().not(".J_menuTabs")),
			i = $(".content-tabs").outerWidth(!0) - a,
			n = 0;
		if ($(".page-tabs-content").width() < i)
			return !1;
		for (var s = $(".J_menuTab:first"), r = 0; r + $(s).outerWidth(!0) <= e;)
			r += $(s).outerWidth(!0), s = $(s).next();
		if (r = 0, t($(s).prevAll()) > i) {
			for (; r + $(s).outerWidth(!0) < i && s.length > 0;)
				r += $(s).outerWidth(!0),
				s = $(s).prev();
			n = t($(s).prevAll())
		}
		$(".page-tabs-content").animate({
			marginLeft: 0 - n + "px"
		}, "0")
	}

	function i() {
		var e = Math.abs(parseInt($(".page-tabs-content").css("margin-left"))),
			a = t($(".content-tabs").children().not(".J_menuTabs")),
			i = $(".content-tabs").outerWidth(!0) - a,
			n = 0;
		if ($(".page-tabs-content").width() < i)
			return !1;
		for (var s = $(".J_menuTab:first"), r = 0; r + $(s).outerWidth(!0) <= e;)
			r += $(s).outerWidth(!0),
			s = $(s).next();
		for (r = 0; r + $(s).outerWidth(!0) < i && s.length > 0;)
			r += $(s).outerWidth(!0), s = $(s).next();
		n = t($(s).prevAll()), n > 0 && $(".page-tabs-content").animate({
			marginLeft: 0 - n + "px"
		}, "0")
	}

	function n() {
		var t = $(this).attr("href"),
			a = $(this).data("index"),
			i = $.trim($(this).text()),
			n = !0;
		$("#side-menu").find("a").removeClass("J_hover");
		$(this).addClass("J_hover");
		if (void 0 == t || 0 == $.trim(t).length) return !1;
		if ($(".J_menuTab").each(function() {
				return $(this).data("id") == t ? ($(this).hasClass("active") || ($(this).addClass("active").siblings(".J_menuTab").removeClass("active"), e(this), $(".J_mainContent .J_iframe").each(function() {
					return $(this).data("id") == t ? ($(this).show().siblings(".J_iframe").hide(), !1) : void 0
				})), n = !1, !1) : void 0
			}), n) {
			var s = '<span class="active J_menuTab positionR J_tabClose2" data-id="' + t + '">' + i + ' <i class="fa fa-times-circle"></i><ul role="menu" class="dropdown-menu dropdown-menu-left"><li class="J_tabCloseAll"><a>关闭全部</a></li><li class="J_tabCloseOther"><a>关闭其他</a></li></ul></span>';
			$(".J_menuTab").removeClass("active");
			var r = '<iframe class="J_iframe" name="iframe' + a + '" width="100%" height="100%" src="' + t + '" frameborder="0" data-id="' + t + '" seamless></iframe>';
			$(".J_mainContent").find("iframe.J_iframe").hide().parents(".J_mainContent").append(r);
			var o = layer.load();
			$(".J_mainContent iframe:visible").load(function() {
				layer.close(o)
			}), $(".J_menuTabs .page-tabs-content").append(s), e($(".J_menuTab.active"))
		}

		return !1
	}

	function s() {
		var t = $(this).parents(".J_menuTab").data("id"),
			a = $(this).parents(".J_menuTab").width();
		if ($(this).parents(".J_menuTab").hasClass("active")) {
			if ($(this).parents(".J_menuTab").next(".J_menuTab").size()) {
				var i = $(this).parents(".J_menuTab").next(".J_menuTab:eq(0)").data("id");
				$(this).parents(".J_menuTab").next(".J_menuTab:eq(0)").addClass("active"), $(".J_mainContent .J_iframe").each(function() {
					return $(this).data("id") == i ? ($(this).show().siblings(".J_iframe").hide(), !1) : void 0
				});

				var n = parseInt($(".page-tabs-content").css("margin-left"));
				0 > n && $(".page-tabs-content").animate({
					marginLeft: n + a + "px"
				}, "0"), $(this).parents(".J_menuTab").remove(), $(".J_mainContent .J_iframe").each(function() {
					return $(this).data("id") == t ? ($(this).remove(), !1) : void 0
				})
			}

			if ($(this).parents(".J_menuTab").prev(".J_menuTab").size()) {
				var i = $(this).parents(".J_menuTab").prev(".J_menuTab:last").data("id");
				$(this).parents(".J_menuTab").prev(".J_menuTab:last").addClass("active"), $(".J_mainContent .J_iframe").each(function() {
					return $(this).data("id") == i ? ($(this).show().siblings(".J_iframe").hide(), !1) : void 0
				}), $(this).parents(".J_menuTab").remove(), $(".J_mainContent .J_iframe").each(function() {
					return $(this).data("id") == t ? ($(this).remove(), !1) : void 0
				})
			}
		} else $(this).parents(".J_menuTab").remove(), $(".J_mainContent .J_iframe").each(function() {
			return $(this).data("id") == t ? ($(this).remove(), !1) : void 0
		}), e($(".J_menuTab.active"));
		return !1
	}

	function r() {
		$(".page-tabs-content").children("[data-id]").not(":first").not(".active").each(function() {
			$('.J_iframe[data-id="' + $(this).data("id") + '"]').remove(), $(this).remove()
		}), $(".page-tabs-content").css("margin-left", "0")
	}

	function o() {
		e($(".J_menuTab.active"))
	}

	function d() {
		if (!$(this).hasClass("active")) {
			var t = $(this).data("id");
			$(".J_mainContent .J_iframe").each(function() {
				return $(this).data("id") == t ? ($(this).show().siblings(".J_iframe").hide(), !1) : void 0
			}), $(this).addClass("active").siblings(".J_menuTab").removeClass("active"), e(this)
		}
	}

	function c() {
		var t = $('.J_iframe[data-id="' + $(this).data("id") + '"]'),
			e = t.attr("src"),
			a = layer.load();
		t.attr("src", e).load(function() {
			layer.close(a)
		})
	}

	$(".J_menuItem").each(function(t) {
			$(this).attr("data-index") || $(this).attr("data-index", t)
		}),
		//$(".nav").on("click",".J_menuItem", n),
		 $("body").on("click",".J_menuItem", n),
		$(".J_menuTabs").on("click", ".J_menuTab i", s),
		$(".J_menuTabs").on("click", ".J_tabCloseOther", r),
		$(".J_menuTabs").on("click", ".J_tabShowActive", o),
		$(".J_menuTabs").on("click", ".J_menuTab", d),
		$(".J_menuTabs").on("dblclick", ".J_menuTab", c),
		$(".J_tabLeft").on("click", a),
		$(".J_tabRight").on("click", i),
		$(".J_menuTabs").on("click", '.J_tabCloseAll', function() {
			$(".page-tabs-content").children("[data-id]").not(":first").each(function() {
					$('.J_iframe[data-id="' + $(this).data("id") + '"]').remove(),
						$(this).remove()
				}),
				$(".page-tabs-content").children("[data-id]:first").each(function() {
					$('.J_iframe[data-id="' + $(this).data("id") + '"]').show(),
						$(this).addClass("active")
				}),
				$(".page-tabs-content").css("margin-left", "0")
		})
});




//新增顶部导航右击事件
window.oncontextmenu = function() {
		return false;
	} //禁止默认右键事件
$('body').on('mousedown', '.J_tabClose2', function(e) {
	if (e.which == 3) {
		$(this).addClass('open').siblings().removeClass('open');
	}
})
$('body').not('.J_tabClose2').on('click', function() {
	$('.J_tabClose2').removeClass('open');
})
$('.J_menuTabs').click(function() {
	$('.J_tabClose2').removeClass('open')
})