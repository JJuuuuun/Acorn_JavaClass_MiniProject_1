package Service;

public interface ISettingService {
    /**
     * TODO: 일정 시간 후 재생을 정지
     *
     * 특정 값을 받아온 후 지정시간만큼 Thread sleep
     * 이후에 PlaybackService > stop 호출
     * SettingsController가 PlaybackService의 인스턴스를 가지고 있어야함
     * InfoController와 같은 방식으로 초기화해야함
     */
    void stopAfter();

}
