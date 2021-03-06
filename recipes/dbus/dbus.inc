DESCRIPTION = "A message bus system for inter-process communication"
HOMEPAGE = "http://dbus.freedesktop.org"
SECTION = "base"
LICENSE = "GPL"
DEPENDS = "expat glib-2.0 virtual/libintl libsm libice virtual/libx11"

SRC_URI = "\
  http://dbus.freedesktop.org/releases/dbus/dbus-${PV}.tar.gz \
  file://cross.patch;patch=1 \
  file://tmpdir.patch;patch=1 \
  file://fix-install-daemon.patch;patch=1 \
  file://0001-Make-the-default-DBus-reply-timeout-configurable.patch;patch=1 \
  file://dbus-1.init \
"

S = "${WORKDIR}/dbus-${PV}"

inherit autotools pkgconfig update-rc.d gettext

EXTRA_OECONF = "\
  --disable-qt \
  --disable-qt3 \
  --disable-gtk \
  --disable-tests \
  --disable-checks \
  --disable-xml-docs \
  --disable-doxygen-docs \
  --with-xml=expat \
  --with-x \
  --with-dbus-default-reply-timeout=60000 \
"

do_install_append() {
        install -d ${D}${sysconfdir}/init.d
        install -m 0755 ${WORKDIR}/dbus-1.init ${D}${sysconfdir}/init.d/dbus-1
        # the stock install seems to install the libtool wrapper script, so we have to copy this manually :M:
        if [ -e bus/.libs/dbus-daemon-launch-helper ]; then
                install -m 0755 bus/.libs/dbus-daemon-launch-helper ${D}${libexecdir}/
        fi
}
do_stage() {
        oe_libinstall -so -C dbus libdbus-1 ${STAGING_LIBDIR}
        autotools_stage_includes
        mkdir -p ${STAGING_LIBDIR}/dbus-1.0/include/dbus/
        install -m 0644 dbus/dbus-arch-deps.h ${STAGING_LIBDIR}/dbus-1.0/include/dbus/
}

RDEPENDS_${PN}-x11 = "${PN}"
RRECOMMENDS_${PN}-lib = "${PN}"
RCONFLICTS_${PN} = "dbus-1"
RREPLACES_${PN} = "dbus-1"
RPROVIDES_${PN} = "dbus-1"

PACKAGES =+ "${PN}-lib ${PN}-x11"

FILES_${PN} = "\
${bindir}/dbus-daemon* \
${bindir}/dbus-uuidgen \
${bindir}/dbus-cleanup-sockets \
${bindir}/dbus-send \
${bindir}/dbus-monitor \
${libexecdir}/dbus* \
${sysconfdir} \
${datadir}/dbus-1/services \
${datadir}/dbus-1/system-services \
"
FILES_${PN}-lib = "${libdir}/lib*.so.*"
FILES_${PN}-x11 = "\
${bindir}/dbus-launch \
"
FILES_${PN}-dev += "${libdir}/dbus-1.0/include ${bindir}/dbus-glib-tool ${libdir}/"

pkg_postinst_${PN}() {
#!/bin/sh
# can't do adduser stuff offline
if [ "x$D" != "x" ]; then
  exit 1
fi
MESSAGEUSER=messagebus
MESSAGEHOME=/var/run/dbus
mkdir -p $MESSAGEHOME || true
grep -q ^$MESSAGEUSER: /etc/group || addgroup "$MESSAGEUSER"
chgrp "$MESSAGEUSER" "$MESSAGEHOME"
grep -q ^$MESSAGEUSER: /etc/passwd || adduser --system --home "$MESSAGEHOME" --no-create-home --disabled-password --ingroup "$MESSAGEUSER" "$MESSAGEUSER"
chown "$MESSAGEUSER"."$MESSAGEUSER" "$MESSAGEHOME"
grep -q netdev: /etc/group || addgroup netdev
chmod u+s /usr/libexec/dbus-daemon-launch-helper
}

CONFFILES_${PN} = "${sysconfdir}/dbus-1/system.conf ${sysconfdir}/dbus-1/session.conf"

INITSCRIPT_NAME = "dbus-1"
INITSCRIPT_PARAMS = "defaults"
