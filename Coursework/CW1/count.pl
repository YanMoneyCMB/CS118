#!/usr/bin/perl -n
BEGIN{$num_ahead=0; $num_left=0; $num_right=0; $num_behind=0;}
$num_ahead++ if/^I'm going forward/;
$num_left++ if/^I'm going left/;
$num_right++ if/^I'm going right/;
$num_behind++ if/^I'm going backwards/;
print"Summary of moves: Forward=$num_ahead Left=$num_left Right=$num_right Backwards=$num_behind\n";

