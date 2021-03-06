DESCRIPTION = "A Type1 Font Rastering Library"
SECTION = "libs"
PRIORITY = "optional"
DEPENDS = "virtual/libx11"
PR = "r4"
LICENSE = "LGPL GPL"
SRC_URI = "${DEBIAN_MIRROR}/main/t/t1lib/t1lib_${PV}.orig.tar.gz \
           file://configure.patch;patch=1 \
           file://install.patch;patch=1 \
	   file://libtool.patch;patch=1"

inherit autotools

EXTRA_OECONF = "--with-x --without-athena"
EXTRA_OEMAKE = "without_doc"

do_configure() {
	rm -f ${S}/ac-tools/aclocal.m4
	autotools_do_configure
}

do_stage() {
	autotools_stage_all
}

FILES_${PN} = "${bindir}/* ${libdir}/*.so* ${datadir}/t1lib/t1lib.config"
FILES_${PN}-doc = "${datadir}/t1lib/doc/t1lib_doc.pdf"
