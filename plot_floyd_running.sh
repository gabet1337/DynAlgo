#!/usr/bin/gnuplot

set terminal pngcairo enhanced font 'Verdana,12'
set title "Floyd-Warshall on the given testdata"
set output 'floyd-given-data-running-time.png'

set xlabel "input size"
set ylabel "running time (ms)"
set xtics autofreq
set grid ytics lc rgb "#bbbbbb" lw 1 lt 0
set grid xtics lc rgb "#bbbbbb" lw 1 lt 0

set xr [0:310]
set key left top

plot "floyd_test.dat" title 'Floyd-Warshall'

