#!/usr/bin/gnuplot

set terminal pngcairo enhanced font 'Verdana,12'
set title "Running time"
set output 'running-time-GOOD.png'

set xlabel "input size"
set ylabel "running time per operation (s) / n squared"
set xtics autofreq
set grid ytics lc rgb "#bbbbbb" lw 1 lt 0
set grid xtics lc rgb "#bbbbbb" lw 1 lt 0

set xr [50:300]
set key left top

plot "data/test_running_var_n_floyd.dat" using 1:($2/($1*$1*20000)) title 'Floyd-Warshall',\
    "data/test_running_var_n_sankowski.dat" using 1:($2/($1*$1*20000)) title 'Sankowski'


