SECTION = "console/network"
DESCRIPTION ="tinc is a Virtual Private Network (VPN) daemon"
HOMEPAGE = "http://www.tinc-vpn.org/"
LICENSE = "GPLv2"

PR = "r0"
PV = "1.0.9+git"
SRCREV = "de029ce46056e02908b5390da9b71a6a59133f26"

SRC_URI="git://www.tinc-vpn.org/git/tinc/;protocol=http;branch=master \
	file://init"
S = "${WORKDIR}/git"

DEPENDS = "openssl lzo zlib"
RRECOMMENDS = "kernel-module-tun"

inherit autotools

EXTRA_OECONF = "--disable-maintainer-mode --disable-tracing"

do_configure_prepend () {
	autoreconf -f -i -s
}

do_configure() {
	oe_runconf
}

do_compile() {
	oe_runmake
}

do_install() {
        oe_runmake install DESTDIR=${D}
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/tinc
}
