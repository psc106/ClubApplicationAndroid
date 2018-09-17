package teamproject.com.clubapplication.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import teamproject.com.clubapplication.data.KoreaLocal;

public class DBManager extends SQLiteOpenHelper {
    private String TAG = "버그";

    public final static String DB_NAME = "com.teamproject.clubapp.db";
    public final static int CURRENT_VERSION = 121;

    private final static String LOCAL_TABLE = "local";
    private final static String LOCAL_CODE = "localCode";
    private final static String LOCAL_DO_SI = "localDoSi";
    private final static String LOCAL_SI_GUN_GU = "localSiGunGu";

    private final static String CATEGORY_TABLE = "category";
    private final static String CATEGORY_CODE = "categoryCode";
    private final static String CATEGORY_NAME = "categoryName";

    private String categoryDatas[] = {"취미1", "취미2", "취미3", "취미4", "취미5", "취미6", "취미7", "취미8", "취미9"};
    private String localDatas[] = {"서울특별시	11010	종로구", "서울특별시	11020	중구", "서울특별시	11030	용산구", "서울특별시	11040	성동구", "서울특별시	11050	광진구", "서울특별시	11060	동대문구", "서울특별시	11070	중랑구",
            "서울특별시	11080	성북구", "서울특별시	11090	강북구", "서울특별시	11100	도봉구", "서울특별시	11110	노원구", "서울특별시	11120	은평구", "서울특별시	11130	서대문구", "서울특별시	11140	마포구",
            "서울특별시	11150	양천구", "서울특별시	11160	강서구", "서울특별시	11170	구로구", "서울특별시	11180	금천구", "서울특별시	11190	영등포구", "서울특별시	11200	동작구", "서울특별시	11210	관악구",
            "서울특별시	11220	서초구", "서울특별시	11230	강남구", "서울특별시	11240	송파구", "서울특별시	11250	강동구", "부산광역시	21010	중구", "부산광역시	21020	서구", "부산광역시	21030	동구",
            "부산광역시	21040	영도구", "부산광역시	21050	부산진구", "부산광역시	21060	동래구", "부산광역시	21070	남구", "부산광역시	21080	북구", "부산광역시	21090	해운대구", "부산광역시	21100	사하구",
            "부산광역시	21110	금정구", "부산광역시	21120	강서구", "부산광역시	21130	연제구", "부산광역시	21140	수영구", "부산광역시	21150	사상구", "부산광역시	21310	기장군", "대구광역시	22010	중구",
            "대구광역시	22020	동구", "대구광역시	22030	서구", "대구광역시	22040	남구", "대구광역시	22050	북구", "대구광역시	22060	수성구", "대구광역시	22070	달서구", "대구광역시	22310	달성군", "인천광역시	23010	중구",
            "인천광역시	23020	동구", "인천광역시	23030	남구", "인천광역시	23040	연수구", "인천광역시	23050	남동구", "인천광역시	23060	부평구", "인천광역시	23070	계양구", "인천광역시	23080	서구", "인천광역시	23310	강화군",
            "인천광역시	23320	옹진군", "광주광역시	24010	동구", "광주광역시	24020	서구", "광주광역시	24030	남구", "광주광역시	24040	북구", "광주광역시	24050	광산구", "대전광역시	25010	동구", "대전광역시	25020	중구",
            "대전광역시	25030	서구", "대전광역시	25040	유성구", "대전광역시	25050	대덕구", "울산광역시	26010	중구", "울산광역시	26020	남구", "울산광역시	26030	동구", "울산광역시	26040	북구", "울산광역시	26310	울주군",
            "세종특별자치시	29010	세종시", "경기도	31010	수원시", "경기도	31011	장안구", "경기도	31012	권선구", "경기도	31013	팔달구", "경기도	31014	영통구", "경기도	31020	성남시", "경기도	31021	수정구",
            "경기도	31022	중원구", "경기도	31023	분당구", "경기도	31030	의정부시", "경기도	31040	안양시", "경기도	31041	만안구", "경기도	31042	동안구", "경기도	31050	부천시", "경기도	31051	원미구",
            "경기도	31052	소사구", "경기도	31053	오정구", "경기도	31060	광명시", "경기도	31070	평택시", "경기도	31080	동두천시", "경기도	31090	안산시", "경기도	31091	상록구", "경기도	31092	단원구",
            "경기도	31100	고양시", "경기도	31101	덕양구", "경기도	31103	일산동구", "경기도	31104	일산서구", "경기도	31110	과천시", "경기도	31120	구리시", "경기도	31130	남양주시", "경기도	31140	오산시",
            "경기도	31150	시흥시", "경기도	31160	군포시", "경기도	31170	의왕시", "경기도	31180	하남시", "경기도	31190	용인시", "경기도	31191	처인구", "경기도	31192	기흥구", "경기도	31193	수지구",
            "경기도	31200	파주시", "경기도	31210	이천시", "경기도	31220	안성시", "경기도	31230	김포시", "경기도	31240	화성시", "경기도	31250	광주시", "경기도	31260	양주시", "경기도	31270	포천시",
            "경기도	31280	여주시", "경기도	31350	연천군", "경기도	31370	가평군", "경기도	31380	양평군", "강원도	32010	춘천시", "강원도	32020	원주시", "강원도	32030	강릉시", "강원도	32040	동해시",
            "강원도	32050	태백시", "강원도	32060	속초시", "강원도	32070	삼척시", "강원도	32310	홍천군", "강원도	32320	횡성군", "강원도	32330	영월군", "강원도	32340	평창군", "강원도	32350	정선군",
            "강원도	32360	철원군", "강원도	32370	화천군", "강원도	32380	양구군", "강원도	32390	인제군", "강원도	32400	고성군", "강원도	32410	양양군", "충청북도	33020	충주시", "충청북도	33030	제천시",
            "충청북도	33040	청주시", "충청북도	33041	상당구", "충청북도	33042	서원구", "충청북도	33043	흥덕구", "충청북도	33044	청원구", "충청북도	33320	보은군", "충청북도	33330	옥천군", "충청북도	33340	영동군",
            "충청북도	33350	진천군", "충청북도	33360	괴산군", "충청북도	33370	음성군", "충청북도	33380	단양군", "충청북도	33390	증평군", "충청남도	34010	천안시", "충청남도	34011	동남구", "충청남도	34012	서북구",
            "충청남도	34020	공주시", "충청남도	34030	보령시", "충청남도	34040	아산시", "충청남도	34050	서산시", "충청남도	34060	논산시", "충청남도	34070	계룡시", "충청남도	34080	당진시", "충청남도	34310	금산군",
            "충청남도	34330	부여군", "충청남도	34340	서천군", "충청남도	34350	청양군", "충청남도	34360	홍성군", "충청남도	34370	예산군", "충청남도	34380	태안군", "전라북도	35010	전주시", "전라북도	35011	완산구",
            "전라북도	35012	덕진구", "전라북도	35020	군산시", "전라북도	35030	익산시", "전라북도	35040	정읍시", "전라북도	35050	남원시", "전라북도	35060	김제시", "전라북도	35310	완주군", "전라북도	35320	진안군",
            "전라북도	35330	무주군", "전라북도	35340	장수군", "전라북도	35350	임실군", "전라북도	35360	순창군", "전라북도	35370	고창군", "전라북도	35380	부안군", "전라남도	36010	목포시", "전라남도	36020	여수시",
            "전라남도	36030	순천시", "전라남도	36040	나주시", "전라남도	36060	광양시", "전라남도	36310	담양군", "전라남도	36320	곡성군", "전라남도	36330	구례군", "전라남도	36350	고흥군", "전라남도	36360	보성군",
            "전라남도	36370	화순군", "전라남도	36380	장흥군", "전라남도	36390	강진군", "전라남도	36400	해남군", "전라남도	36410	영암군", "전라남도	36420	무안군", "전라남도	36430	함평군", "전라남도	36440	영광군",
            "전라남도	36450	장성군", "전라남도	36460	완도군", "전라남도	36470	진도군", "전라남도	36480	신안군", "경상북도	37010	포항시", "경상북도	37011	남구", "경상북도	37012	북구", "경상북도	37020	경주시",
            "경상북도	37030	김천시", "경상북도	37040	안동시", "경상북도	37050	구미시", "경상북도	37060	영주시", "경상북도	37070	영천시", "경상북도	37080	상주시", "경상북도	37090	문경시", "경상북도	37100	경산시",
            "경상북도	37310	군위군", "경상북도	37320	의성군", "경상북도	37330	청송군", "경상북도	37340	영양군", "경상북도	37350	영덕군", "경상북도	37360	청도군", "경상북도	37370	고령군", "경상북도	37380	성주군",
            "경상북도	37390	칠곡군", "경상북도	37400	예천군", "경상북도	37410	봉화군", "경상북도	37420	울진군", "경상북도	37430	울릉군", "경상남도	38030	진주시", "경상남도	38050	통영시", "경상남도	38060	사천시",
            "경상남도	38070	김해시", "경상남도	38080	밀양시", "경상남도	38090	거제시", "경상남도	38100	양산시", "경상남도	38110	창원시", "경상남도	38111	의창구", "경상남도	38112	성산구", "경상남도	38113	마산합포구",
            "경상남도	38114	마산회원구", "경상남도	38115	진해구", "경상남도	38310	의령군", "경상남도	38320	함안군", "경상남도	38330	창녕군", "경상남도	38340	고성군", "경상남도	38350	남해군", "경상남도	38360	하동군",
            "경상남도	38370	산청군", "경상남도	38380	함양군", "경상남도	38390	거창군", "경상남도	38400	합천군", "제주특별자치도	39010	제주시", "제주특별자치도	39020	서귀포시"};

    private String query = "";

    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        query = String.format("CREATE TABLE '%s' ", LOCAL_TABLE) +
                String.format("( '%s' INTEGER PRIMARY KEY AUTOINCREMENT" +
                                ", '%s' TEXT" +
                                ", '%s' TEXT)",
                        LOCAL_CODE, LOCAL_DO_SI, LOCAL_SI_GUN_GU);
        db.execSQL(query);

        for (int i = 0; i < localDatas.length; ++i) {
            String[] tmp = localDatas[i].split(("\t"));
            query = String.format("INSERT INTO %s ", LOCAL_TABLE) +
                    String.format("VALUES(%s, '%s', '%s')", tmp[1], tmp[0], tmp[2]);
            db.execSQL(query);
        }

        query = String.format("CREATE TABLE '%s' ", CATEGORY_TABLE) +
                String.format("( '%s' INTEGER PRIMARY KEY AUTOINCREMENT" +
                                ", '%s' TEXT)",
                        CATEGORY_CODE, CATEGORY_NAME);
        db.execSQL(query);

        for (int i = 1; i < categoryDatas.length; ++i) {
            query = String.format("INSERT INTO %s ", CATEGORY_TABLE) +
                    String.format("VALUES(%d, '%s')", i, categoryDatas[i - 1]);
            db.execSQL(query);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        query = "DROP TABLE " + LOCAL_TABLE;
        db.execSQL(query);
        Log.d(TAG, "onUpgrade: "+query);
        query = "DROP TABLE " + CATEGORY_TABLE;
        db.execSQL(query);
        Log.d(TAG, "onUpgrade: "+query);
        onCreate(db);
    }

    public String[] getDoSi() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();

        query = String.format("SELECT DISTINCT %s ", LOCAL_DO_SI) +
                String.format(" FROM %s ", LOCAL_TABLE) +
                String.format(" ORDER BY %s ", LOCAL_CODE);
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            arrayList.add(cursor.getString(0));
        }
        return arrayList.toArray(new String[0]);
    }

    public String[] getSiGunGu() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();

        query = String.format("SELECT %s ", LOCAL_SI_GUN_GU) +
                String.format(" FROM %s ", LOCAL_TABLE);
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            arrayList.add(cursor.getString(0));
        }
        return arrayList.toArray(new String[0]);
    }


    public String[] getSiGunGuFromDoSi(String doSi) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();

        query = String.format("SELECT %s ", LOCAL_SI_GUN_GU) +
                String.format(" FROM %s ", LOCAL_TABLE) +
                String.format(" WHERE %s like '%s' ", LOCAL_DO_SI, doSi);
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            arrayList.add(cursor.getString(0));
        }
        return arrayList.toArray(new String[0]);
    }

    public KoreaLocal getLocalData(Integer code) {
        SQLiteDatabase db = getReadableDatabase();
        KoreaLocal koreaLocal = null;

        query = "SELECT * " +
                String.format(" FROM %s ", LOCAL_TABLE) +
                String.format(" WHERE %s = %d ", LOCAL_CODE, code);
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            koreaLocal = new KoreaLocal(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
        }
        return koreaLocal;
    }


    public String[] getCategory() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();

        query = String.format("SELECT %s ", CATEGORY_NAME) +
                String.format(" FROM %s ", CATEGORY_TABLE);
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            arrayList.add(cursor.getString(0));
        }
        return arrayList.toArray(new String[0]);
    }


    public String getCategoryFromId(Long id) {
        SQLiteDatabase db = getReadableDatabase();
        String result = null;

        query = String.format("SELECT %s ", CATEGORY_NAME) +
                String.format(" FROM %s ", CATEGORY_TABLE) +
                String.format(" WHERE %s = %d ", CATEGORY_CODE, id);
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            result = cursor.getString(0);
        }
        return result;
    }
//        Log.d(TAG, "dbsc3: "+arrayList);
//        Log.d(TAG, "getCurrentEventCountOnTime: "+query);
}
