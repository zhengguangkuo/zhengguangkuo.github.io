#!/bin/bash

echo 'mitenoframework-ios template installer'

MITENO_IOS_VER='mitenoframework-ios 0.1'
SCRIPT_DIR=$(dirname $0)

MITENO_IOS_DST_DIR='mitenoframework'

force=

usage(){
cat << EOF
usage: $0 [options]
 
Install / update templates for ${MITENO_IOS_VER}
 
OPTIONS:
   -f	force overwrite if directories exist
   -h	this help
EOF
}

while getopts "fhu" OPTION; do
	case "$OPTION" in
		f)
			force=1
			;;
		h)
			usage
			exit 0
			;;
		u)
			;;
	esac
done

# Make sure root is not executed
#if [[ "$(id -u)" == "0" ]]; then
#	echo ""
#	echo "Error: Do not run this script as root." 1>&2
#	echo ""
#	echo "'root' is no longer supported" 1>&2
#	echo ""
#	echo "RECOMMENDED WAY:" 1>&2
#	echo " $0 -f" 1>&2
#	echo ""
#exit 1
#fi


copy_files(){
    SRC_DIR="${SCRIPT_DIR}/${1}"
	rsync -r --exclude=.svn "$SRC_DIR" "$2"
}

check_dst_dir(){
	if [[ -d $DST_DIR ]];  then
		if [[ $force ]]; then
			echo "removing old libraries: ${DST_DIR}"
			rm -rf "$DST_DIR"
		else
			echo "templates already installed. To force a re-install use the '-f' parameter"
			exit 1
		fi
	fi
	
	echo ...creating destination directory: $DST_DIR
	mkdir -p "$DST_DIR"
}

print_template_banner(){
	echo ''
	echo ''
	echo ''
	echo "$1"
	echo '----------------------------------------------------'
	echo ''
}



# Xcode4 templates
copy_xcode4_project_templates(){
	TEMPLATE_DIR="$HOME/Library/Developer/Xcode/Templates/$MITENO_IOS_DST_DIR/"

	print_template_banner "Installing Xcode 4 Mitenoframework iOS template"

	DST_DIR="$TEMPLATE_DIR"
    check_dst_dir

	echo ...copying template files
	copy_files templates/Xcode4_templates/ "$DST_DIR"

       print_template_banner "Installing Xcode 4 Mitenoframework iOS template finished!"
       echo done!
}

# copy Xcode4 templates
copy_xcode4_project_templates

