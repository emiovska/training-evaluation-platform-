import { Component, OnInit } from '@angular/core';


interface Image {
  src: string;
  text: string;
}
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  images: Array<Image>;
  constructor() { }

  ngOnInit() {
    this.images = [
      { src: 'assets/images/training7.jpg', text: 'home.TrainingSkills' },
      { src: 'assets/images/training5.jpg', text: 'home.AchieveGoals' },
      { src: 'assets/images/training6.jpg', text: 'home.GainExperience' },
      { src: 'assets/images/training4.jpg', text: 'home.ImproveKnowledge' }
    ];
  }
}
