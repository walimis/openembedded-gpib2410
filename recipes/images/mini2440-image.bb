#Angstrom bootstrap image

IMAGE_PREPROCESS_COMMAND = "create_etc_timestamp"

ANGSTROM_EXTRA_INSTALL ?= ""

DEPENDS =  "task-base-extended \
            psplash-zap \
	    esekeyd u-boot-utils tslib \
	    i2c-tools i2c screen rsync nfs-utils \
	    directfb gdbserver directfb mtd-utils \
	   "

IMAGE_INSTALL = "task-base-extended \
	    ${ANGSTROM_EXTRA_INSTALL} \
	    psplash-zap \
	    esekeyd u-boot-utils tslib-calibrate tslib-tests \
	    i2c-tools i2c screen rsync nfs-utils-client \
	    directfb gdbserver directfb mtd-utils \
	    rsvg pango \
	   "

export IMAGE_BASENAME = "mini2440-image"
IMAGE_LINGUAS = ""

inherit image

