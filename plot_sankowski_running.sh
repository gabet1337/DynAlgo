#!/usr/bin/gnuplot

set terminal pngcairo enhanced font 'Verdana,12'
set title "Sankowski on the given testdata"
set output 'sankowski-given-data-running-time.png'

set xlabel "input size"
set ylabel "running time per operation (ms)"
set xtics autofreq
set grid ytics lc rgb "#bbbbbb" lw 1 lt 0
set grid xtics lc rgb "#bbbbbb" lw 1 lt 0

set xr [0:1100]
set key left top

plot "data/sankowski_test.dat" using 1:($2 / (3*($1**2))) title 'Sankowski'

