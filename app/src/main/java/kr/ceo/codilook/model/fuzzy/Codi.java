package kr.ceo.codilook.model.fuzzy;

import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings("NonAsciiCharacters")
public enum Codi implements Parcelable {
    댄디, 라이더, 란제리, 레이어드, 레트로, 로맨틱, 매니쉬, 모던, 미니멀, 믹스앤매치, 빈티지, 스트릿_무난함, 스트릿_화려함, 시스루, 아메카지, 애슬레저, 얼씨, 오피스, 워크웨어, 웨스턴무드, 유틸리티, 집시, 캐주얼, 컨트리, 클래식, 펑크, 페미닌, 프레피, 히피;

    public static final Creator<Codi> CREATOR = new Creator<Codi>() {
        @Override
        public Codi createFromParcel(Parcel source) {
            return Codi.valueOf(source.readString());
        }

        @Override
        public Codi[] newArray(int size) {
            return new Codi[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name());
    }
}