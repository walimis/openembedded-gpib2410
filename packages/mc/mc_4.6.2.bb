require mc.inc
HOMEPAGE = "http://www.midnight-commander.org/"

# UTF-8 fixes copied from openSUSE Factory:
# (2009-02-12 by nadvornik@suse.cz)
# mc-utf8-look-and-feel.patch: debian fix for utf8
SRC_URI = "http://www.midnight-commander.org/downloads/${P}.tar.gz \
	   file://mhl-stdbool.patch;patch=1 \
	   file://mc-utf8.patch;patch=1 \
	   file://00-70-utf8-common.patch;patch=1 \
	   file://00-73-utf8-bottom-buttons-width.patch;patch=1 \
	   file://00-75-utf8-cmdline-help.patch;patch=1 \
	   file://00-76-utf8-hotlist-highlight.patch;patch=1 \
	   file://00-77-utf8-filename-search-highlight.patch;patch=1 \
	   file://mc-utf8-nlink.patch;patch=1 \
	   file://mc-utf8-look-and-feel.patch;patch=1"

EXTRA_OECONF = "--enable-charset --libexecdir=${libdir}"

do_unpack_append() {
        bb.build.exec_func('do_utf8_conversion', d)
}
do_utf8_conversion() {
	pwd
	pushd lib
	iconv -f iso8859-1 -t utf-8 -o mc.hint.tmp mc.hint && mv mc.hint.tmp mc.hint
	iconv -f iso8859-1 -t utf-8 -o mc.hint.es.tmp mc.hint.es && mv mc.hint.es.tmp mc.hint.es
	iconv -f iso8859-1 -t utf-8 -o mc.hint.it.tmp mc.hint.it && mv mc.hint.it.tmp mc.hint.it
	iconv -f iso8859-1 -t utf-8 -o mc.hint.nl.tmp mc.hint.nl && mv mc.hint.nl.tmp mc.hint.nl
	iconv -f iso8859-2 -t utf-8 -o mc.hint.cs.tmp mc.hint.cs && mv mc.hint.cs.tmp mc.hint.cs
	iconv -f iso8859-2 -t utf-8 -o mc.hint.hu.tmp mc.hint.hu && mv mc.hint.hu.tmp mc.hint.hu
	iconv -f iso8859-2 -t utf-8 -o mc.hint.pl.tmp mc.hint.pl && mv mc.hint.pl.tmp mc.hint.pl
	iconv -f koi8-r -t utf8 -o mc.hint.ru.tmp mc.hint.ru && mv mc.hint.ru.tmp mc.hint.ru
	iconv -f koi8-u -t utf8 -o mc.hint.uk.tmp mc.hint.uk && mv mc.hint.uk.tmp mc.hint.uk
	iconv -f big5 -t utf8 -o mc.hint.zh.tmp mc.hint.zh && mv mc.hint.zh.tmp mc.hint.zh
	popd
	# convert docs to utf-8
	pushd doc
	pushd es
	iconv -f iso8859-1 -t utf-8 -o mc.1.in.tmp mc.1.in && mv mc.1.in.tmp mc.1.in
	iconv -f iso8859-1 -t utf-8 -o xnc.hlp.tmp xnc.hlp && mv xnc.hlp.tmp xnc.hlp
	popd
	pushd hu
	iconv -f iso8859-2 -t utf-8 -o mc.1.in.tmp mc.1.in && mv mc.1.in.tmp mc.1.in
	iconv -f iso8859-2 -t utf-8 -o xnc.hlp.tmp xnc.hlp && mv xnc.hlp.tmp xnc.hlp
	popd
	pushd it
	iconv -f iso8859-1 -t utf-8 -o mc.1.in.tmp mc.1.in && mv mc.1.in.tmp mc.1.in
	iconv -f iso8859-1 -t utf-8 -o xnc.hlp.tmp xnc.hlp && mv xnc.hlp.tmp xnc.hlp
	popd
	pushd pl
	iconv -f iso8859-2 -t utf-8 -o mc.1.in.tmp mc.1.in && mv mc.1.in.tmp mc.1.in
	iconv -f iso8859-2 -t utf-8 -o xnc.hlp.tmp xnc.hlp && mv xnc.hlp.tmp xnc.hlp
	popd
	pushd ru
	iconv -f koi8-r -t utf-8 -o mc.1.in.tmp mc.1.in && mv mc.1.in.tmp mc.1.in
	iconv -f koi8-r -t utf-8 -o xnc.hlp.tmp xnc.hlp && mv xnc.hlp.tmp xnc.hlp
	popd
	popd
}