{pkgs ? import <nixpkgs> {}}:
pkgs.mkShell {
  shellHook = ''
    export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:${pkgs.lib.makeLibraryPath [
      pkgs.xorg.libXtst
      pkgs.libGL
    ]};
  '';
}
